package com.pcbuilder.build.service.impl;

import com.pcbuilder.build.client.CatalogClient;
import com.pcbuilder.build.client.CompatibilityClient;
import com.pcbuilder.build.dto.*;
import com.pcbuilder.build.entity.ComponentSelection;
import com.pcbuilder.build.entity.PcBuild;
import com.pcbuilder.build.repository.PcBuildRepository;
import com.pcbuilder.build.service.BuildService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class BuildServiceImpl implements BuildService {
    private final PcBuildRepository buildRepository;
    private final CatalogClient catalogClient;
    private final CompatibilityClient compatibilityClient;

    public BuildServiceImpl(PcBuildRepository buildRepository, CatalogClient catalogClient,
                            CompatibilityClient compatibilityClient) {
        this.buildRepository = buildRepository;
        this.catalogClient = catalogClient;
        this.compatibilityClient = compatibilityClient;
    }

    @Override
    public BuildResponse create(BuildRequest request) {
        PcBuild build = new PcBuild();
        applyRequest(build, request);
        Evaluation evaluation = evaluate(build);
        applyEvaluation(build, evaluation);
        return toResponse(buildRepository.save(build), evaluation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuildResponse> getAll() {
        return buildRepository.findAll().stream()
                .map(build -> toResponse(build, evaluate(build)))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BuildResponse getById(Long id) {
        PcBuild build = findBuild(id);
        return toResponse(build, evaluate(build));
    }

    @Override
    public BuildResponse update(Long id, BuildRequest request) {
        PcBuild build = findBuild(id);
        applyRequest(build, request);
        Evaluation evaluation = evaluate(build);
        applyEvaluation(build, evaluation);
        return toResponse(buildRepository.save(build), evaluation);
    }

    @Override
    public BuildResponse validate(Long id) {
        PcBuild build = findBuild(id);
        Evaluation evaluation = evaluate(build);
        applyEvaluation(build, evaluation);
        return toResponse(buildRepository.save(build), evaluation);
    }

    @Override
    public void delete(Long id) {
        buildRepository.delete(findBuild(id));
    }

    private PcBuild findBuild(Long id) {
        return buildRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Build not found with ID: " + id));
    }

    private void applyRequest(PcBuild build, BuildRequest request) {
        build.setName(request.getName().trim());
        build.setCpuId(request.getCpuId());
        build.setMotherboardId(request.getMotherboardId());
        build.setGpuId(request.getGpuId());
        build.setCoolerId(request.getCoolerId());
        build.setPsuId(request.getPsuId());
        build.setPcCaseId(request.getPcCaseId());
        build.setRamSelection(toEntitySelection(request.getRamSelection()));
        build.setStorageSelections(toEntitySelections(request.getStorageSelections()));
        build.setCaseFanSelections(toEntitySelections(request.getCaseFanSelections()));
    }

    private ComponentSelection toEntitySelection(SelectionDto selection) {
        return selection == null ? null : new ComponentSelection(selection.getComponentId(), selection.getQuantity());
    }

    private List<ComponentSelection> toEntitySelections(List<SelectionDto> selections) {
        if (selections == null) return new ArrayList<>();
        return selections.stream()
                .filter(Objects::nonNull)
                .map(this::toEntitySelection)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    private Evaluation evaluate(PcBuild build) {
        Map<Long, Integer> quantities = componentQuantities(build);
        List<Long> ids = new ArrayList<>(quantities.keySet());
        List<CatalogComponentDto> catalogComponents = ids.isEmpty()
                ? List.of()
                : Optional.ofNullable(catalogClient.getComponentsBulk(new BulkLookupRequest(ids))).orElse(List.of());

        Map<Long, CatalogComponentDto> byId = new HashMap<>();
        catalogComponents.forEach(component -> byId.put(component.getId(), component));
        List<Long> missingIds = ids.stream().filter(id -> !byId.containsKey(id)).toList();
        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("The following component IDs do not exist: " + missingIds);
        }

        validateCategory(byId, build.getCpuId(), "CPU");
        validateCategory(byId, build.getMotherboardId(), "MOTHERBOARD");
        validateCategory(byId, build.getGpuId(), "GPU");
        validateCategory(byId, build.getCoolerId(), "COOLER");
        validateCategory(byId, build.getPsuId(), "PSU");
        validateCategory(byId, build.getPcCaseId(), "CASE");
        validateSelectionCategories(byId, build.getRamSelection(), "RAM");
        build.getStorageSelections().forEach(selection -> validateSelectionCategories(byId, selection, "STORAGE"));
        build.getCaseFanSelections().forEach(selection -> validateSelectionCategories(byId, selection, "CASE_FAN"));

        BigDecimal total = quantities.entrySet().stream()
                .map(entry -> byId.get(entry.getKey()).getPrice()
                        .multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CompatibilityResultDto compatibility = compatibilityClient.checkCompatibility(toCompatibilityRequest(build));
        if (compatibility == null) compatibility = new CompatibilityResultDto();
        return new Evaluation(byId, quantities, total, compatibility);
    }

    private Map<Long, Integer> componentQuantities(PcBuild build) {
        Map<Long, Integer> quantities = new LinkedHashMap<>();
        addSingle(quantities, build.getCpuId());
        addSingle(quantities, build.getMotherboardId());
        addSingle(quantities, build.getGpuId());
        addSingle(quantities, build.getCoolerId());
        addSingle(quantities, build.getPsuId());
        addSingle(quantities, build.getPcCaseId());
        addSelection(quantities, build.getRamSelection());
        build.getStorageSelections().forEach(selection -> addSelection(quantities, selection));
        build.getCaseFanSelections().forEach(selection -> addSelection(quantities, selection));
        return quantities;
    }

    private void addSingle(Map<Long, Integer> quantities, Long id) {
        if (id != null) quantities.merge(id, 1, Integer::sum);
    }

    private void addSelection(Map<Long, Integer> quantities, ComponentSelection selection) {
        if (selection != null && selection.getComponentId() != null) {
            quantities.merge(selection.getComponentId(), selection.getQuantity(), Integer::sum);
        }
    }

    private void validateCategory(Map<Long, CatalogComponentDto> components, Long id, String expected) {
        if (id == null) return;
        CatalogComponentDto component = components.get(id);
        if (!expected.equalsIgnoreCase(component.getCategoryType())) {
            throw new IllegalArgumentException("Component " + id + " must be of category " + expected + ".");
        }
    }

    private void validateSelectionCategories(Map<Long, CatalogComponentDto> components,
                                             ComponentSelection selection, String expected) {
        if (selection != null) validateCategory(components, selection.getComponentId(), expected);
    }

    private CompatibilityRequest toCompatibilityRequest(PcBuild build) {
        CompatibilityRequest request = new CompatibilityRequest();
        request.setCpuId(build.getCpuId());
        request.setMotherboardId(build.getMotherboardId());
        request.setGpuId(build.getGpuId());
        request.setCoolerId(build.getCoolerId());
        request.setPsuId(build.getPsuId());
        request.setPcCaseId(build.getPcCaseId());
        request.setRamSelection(toDtoSelection(build.getRamSelection()));
        request.setStorageSelections(build.getStorageSelections().stream().map(this::toDtoSelection).toList());
        request.setCaseFanSelections(build.getCaseFanSelections().stream().map(this::toDtoSelection).toList());
        return request;
    }

    private SelectionDto toDtoSelection(ComponentSelection selection) {
        if (selection == null) return null;
        SelectionDto dto = new SelectionDto();
        dto.setComponentId(selection.getComponentId());
        dto.setQuantity(selection.getQuantity());
        return dto;
    }

    private void applyEvaluation(PcBuild build, Evaluation evaluation) {
        build.setTotalPrice(evaluation.totalPrice());
        build.setCompatible(evaluation.compatibility().isCompatible());
        build.setEstimatedWattage(evaluation.compatibility().getEstimatedWattage());
    }

    private BuildResponse toResponse(PcBuild build, Evaluation evaluation) {
        BuildResponse response = new BuildResponse();
        response.setId(build.getId());
        response.setName(build.getName());
        response.setTotalPrice(evaluation.totalPrice());
        response.setCompatible(evaluation.compatibility().isCompatible());
        response.setEstimatedWattage(evaluation.compatibility().getEstimatedWattage());
        response.setCompatibilityMessages(evaluation.compatibility().getMessages());
        response.setCompatibilitySuggestions(evaluation.compatibility().getSuggestions());
        response.setCreatedAt(build.getCreatedAt());
        response.setUpdatedAt(build.getUpdatedAt());

        evaluation.quantities().forEach((id, quantity) -> {
            CatalogComponentDto component = evaluation.components().get(id);
            response.getComponents().add(new BuildItemDto(
                    id, component.getCategoryType(), component.getName(), component.getBrand(), component.getModel(),
                    quantity, component.getPrice(), component.getPrice().multiply(BigDecimal.valueOf(quantity))));
        });
        return response;
    }

    private record Evaluation(Map<Long, CatalogComponentDto> components, Map<Long, Integer> quantities,
                              BigDecimal totalPrice, CompatibilityResultDto compatibility) {
    }
}
