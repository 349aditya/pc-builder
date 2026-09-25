package com.pcbuilder.compatibility.service.impl;

import com.pcbuilder.compatibility.client.CatalogClient;
import com.pcbuilder.compatibility.dto.*;
import com.pcbuilder.compatibility.rule.CompatibilityContext;
import com.pcbuilder.compatibility.rule.CompatibilityRule;
import com.pcbuilder.compatibility.service.CompatibilityService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompatibilityServiceImpl implements CompatibilityService {

    private final CatalogClient catalogClient;
    private final List<CompatibilityRule> rules;

    public CompatibilityServiceImpl(CatalogClient catalogClient, List<CompatibilityRule> rules) {
        this.catalogClient = catalogClient;
        this.rules = rules;
    }

    @Override
    public CompatibilityResult checkCompatibility(CompatibilityRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Compatibility request cannot be null.");
        }

        Set<Long> requestedIdSet = extractComponentIds(request);

        List<ComponentDto> resolvedDtos = Collections.emptyList();

        if (!requestedIdSet.isEmpty()) {
            List<Long> requestedIds = new ArrayList<>(requestedIdSet);

            resolvedDtos = catalogClient.getComponentsBulk(new BulkLookupRequest(requestedIds));

            if (resolvedDtos == null) {
                resolvedDtos = Collections.emptyList();
            }

            Set<Long> fetchedIds = resolvedDtos.stream()
                    .map(ComponentDto::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Set<Long> missingIds = requestedIds.stream()
                    .filter(id -> !fetchedIds.contains(id))
                    .collect(Collectors.toSet());

            if (!missingIds.isEmpty()) {
                throw new IllegalArgumentException(
                        "Component resolution failed. The following ID(s) do not exist in the catalog: " + missingIds
                );
            }
        }

        CompatibilityContext context = new CompatibilityContext(request, resolvedDtos);

        CompatibilityResult result = new CompatibilityResult();

        for (CompatibilityRule rule : rules) {
            rule.evaluate(context, result);
        }

        result.getMessages().forEach(message -> result.addSuggestion(suggestionFor(message.getRuleName())));

        return result;
    }

    private String suggestionFor(String ruleName) {
        return switch (ruleName) {
            case "CPU_SOCKET_MATCH" -> "Choose a motherboard with the same socket as the selected CPU.";
            case "COOLER_SOCKET_SUPPORT" -> "Choose a CPU cooler that explicitly supports the selected CPU socket.";
            case "RAM_GENERATION_MATCH" -> "Choose RAM with the generation supported by the selected motherboard.";
            case "RAM_SLOT_AND_CAPACITY_LIMIT" -> "Use fewer RAM sticks or a lower total capacity within motherboard limits.";
            case "GPU_LENGTH_CLEARANCE" -> "Choose a shorter GPU or a case with greater GPU clearance.";
            case "COOLER_HEIGHT_CLEARANCE" -> "Choose a shorter air cooler or a case with greater cooler clearance.";
            case "COOLER_RADIATOR_CLEARANCE" -> "Choose a smaller radiator or a case that supports the selected radiator size.";
            case "PSU_POWER_DEFICIT", "PSU_OVERHEAD_RECOMMENDATION" -> "Choose a PSU with a higher wattage rating and suitable power headroom.";
            case "M2_SLOTS_DEPLETED" -> "Reduce M.2 drives or choose a motherboard with more M.2 slots.";
            case "SATA_PORTS_DEPLETED" -> "Reduce SATA drives or choose a motherboard with more SATA ports.";
            case "CASE_FAN_LIMITS_EXCEEDED" -> "Reduce the fan count or choose a case with more compatible fan mounts.";
            case "CASE_FORM_FACTOR_MATCH" -> "Choose a case that supports the selected motherboard form factor.";
            default -> "Review the affected components and replace one with a compatible alternative.";
        };
    }

    private Set<Long> extractComponentIds(CompatibilityRequest request) {
        Set<Long> ids = new LinkedHashSet<>();

        if (request.getCpuId() != null) ids.add(request.getCpuId());
        if (request.getMotherboardId() != null) ids.add(request.getMotherboardId());
        if (request.getGpuId() != null) ids.add(request.getGpuId());
        if (request.getCoolerId() != null) ids.add(request.getCoolerId());
        if (request.getPsuId() != null) ids.add(request.getPsuId());
        if (request.getPcCaseId() != null) ids.add(request.getPcCaseId());

        if (request.getRamSelection() != null && request.getRamSelection().getComponentId() != null) {
            ids.add(request.getRamSelection().getComponentId());
        }

        if (request.getStorageSelections() != null) {
            for (Selection selection : request.getStorageSelections()) {
                if (selection != null && selection.getComponentId() != null) {
                    ids.add(selection.getComponentId());
                }
            }
        }

        if (request.getCaseFanSelections() != null) {
            for (Selection selection : request.getCaseFanSelections()) {
                if (selection != null && selection.getComponentId() != null) {
                    ids.add(selection.getComponentId());
                }
            }
        }

        return ids;
    }
}
