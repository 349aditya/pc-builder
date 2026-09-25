package com.pcbuilder.catalog.service.impl;

import com.pcbuilder.catalog.dto.ComponentDto;
import com.pcbuilder.catalog.mapper.ComponentMapper;
import com.pcbuilder.catalog.repository.ComponentRepository;
import com.pcbuilder.catalog.service.ComponentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.math.BigDecimal;
import java.util.Locale;
import com.pcbuilder.catalog.entity.*;
import org.springframework.data.jpa.domain.Specification;

@Service
@Transactional(readOnly = true)
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentServiceImpl(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public Page<ComponentDto> getAllComponents(Pageable pageable) {
        return componentRepository.findAll(pageable)
                .map(ComponentMapper::toDto);
    }

    @Override
    public Page<ComponentDto> findComponents(String category, String brand, String keyword,
                                             BigDecimal minPrice, BigDecimal maxPrice, String socketType,
                                             String ramType, Integer minWattage, Integer maxLengthMm,
                                             Pageable pageable) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice.");
        }

        Specification<Component> specification = (root, query, builder) -> builder.conjunction();
        if (category != null && !category.isBlank()) {
            Class<? extends Component> type = categoryClass(category);
            specification = specification.and((root, query, builder) -> builder.equal(root.type(), type));
        }
        if (brand != null && !brand.isBlank()) {
            String normalizedBrand = brand.trim().toLowerCase(Locale.ROOT);
            specification = specification.and((root, query, builder) ->
                    builder.equal(builder.lower(root.get("brand")), normalizedBrand));
        }
        if (keyword != null && !keyword.isBlank()) {
            String search = "%" + keyword.trim().toLowerCase(Locale.ROOT) + "%";
            specification = specification.and((root, query, builder) -> builder.or(
                    builder.like(builder.lower(root.get("name")), search),
                    builder.like(builder.lower(root.get("brand")), search),
                    builder.like(builder.lower(root.get("model")), search)));
        }
        if (minPrice != null) {
            specification = specification.and((root, query, builder) ->
                    builder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and((root, query, builder) ->
                    builder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (socketType != null && !socketType.isBlank()) {
            String socket = socketType.trim().toLowerCase(Locale.ROOT);
            specification = specification.and((root, query, builder) -> builder.or(
                    builder.equal(builder.lower(builder.treat(root, Cpu.class).get("socketType")), socket),
                    builder.equal(builder.lower(builder.treat(root, Motherboard.class).get("socketType")), socket)));
        }
        if (ramType != null && !ramType.isBlank()) {
            String memory = ramType.trim().toLowerCase(Locale.ROOT);
            specification = specification.and((root, query, builder) -> builder.or(
                    builder.equal(builder.lower(builder.treat(root, Ram.class).get("ramType")), memory),
                    builder.equal(builder.lower(builder.treat(root, Motherboard.class).get("ramType")), memory)));
        }
        if (minWattage != null) {
            specification = specification.and((root, query, builder) ->
                    builder.greaterThanOrEqualTo(builder.treat(root, PowerSupply.class).get("wattage"), minWattage));
        }
        if (maxLengthMm != null) {
            specification = specification.and((root, query, builder) ->
                    builder.lessThanOrEqualTo(builder.treat(root, Gpu.class).get("lengthMm"), maxLengthMm));
        }
        return componentRepository.findAll(specification, pageable).map(ComponentMapper::toDto);
    }

    private Class<? extends Component> categoryClass(String category) {
        return switch (category.trim().toUpperCase(Locale.ROOT).replace('-', '_')) {
            case "CPU" -> Cpu.class;
            case "MOTHERBOARD" -> Motherboard.class;
            case "RAM" -> Ram.class;
            case "GPU" -> Gpu.class;
            case "CASE" -> PcCase.class;
            case "PSU", "POWER_SUPPLY" -> PowerSupply.class;
            case "STORAGE" -> Storage.class;
            case "COOLER", "CPU_COOLER" -> CpuCooler.class;
            case "CASE_FAN", "FAN" -> CaseFan.class;
            default -> throw new IllegalArgumentException("Unsupported component category: " + category);
        };
    }

    @Override
    public Page<ComponentDto> getComponentsByCategory(String category, Pageable pageable) {
        String normalized = category.toUpperCase(java.util.Locale.ROOT);
        if (!java.util.Set.of("CPU", "MOTHERBOARD", "GPU", "RAM", "STORAGE", "PSU", "CASE", "COOLER", "CASE_FAN").contains(normalized)) {
            throw new IllegalArgumentException("Unknown component category: " + category);
        }
        return componentRepository.findByCategory(normalized, pageable).map(ComponentMapper::toDto);
    }

    @Override
    public ComponentDto getComponentById(Long id) {
        return componentRepository.findById(id)
                .map(ComponentMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Component not found with ID: " + id));
    }

    @Override
    public List<ComponentDto> searchComponents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }
        return componentRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(ComponentMapper::toDto)
                .toList();
    }

    @Override
    public List<ComponentDto> getComponentsByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            return List.of();
        }
        return componentRepository.findByBrandIgnoreCase(brand)
                .stream()
                .map(ComponentMapper::toDto)
                .toList();
    }

    @Override
    public List<ComponentDto> getLowStockComponents(Integer threshold) {
        int limit = (threshold != null) ? threshold : 5;
        return componentRepository.findByStockQuantityLessThan(limit)
                .stream()
                .map(ComponentMapper::toDto)
                .toList();
    }

    @Override
    public List<ComponentDto> getComponentsBulk(List<Long> componentIds) {
        if (componentIds == null || componentIds.isEmpty()) {
            return List.of();
        }
        return componentRepository.findAllById(componentIds)
                .stream()
                .map(ComponentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ComponentDto createComponent(ComponentDto component) {
        validateCommonFields(component);
        return ComponentMapper.toDto(componentRepository.save(ComponentMapper.toEntity(component)));
    }

    @Override
    @Transactional
    public ComponentDto updateComponent(Long id, ComponentDto component) {
        validateCommonFields(component);
        Component entity = componentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Component not found with ID: " + id));
        ComponentMapper.updateEntity(entity, component);
        return ComponentMapper.toDto(componentRepository.save(entity));
    }

    @Override
    @Transactional
    public ComponentDto updateStock(Long id, Integer stockQuantity) {
        if (stockQuantity == null || stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity must be zero or greater.");
        }
        Component entity = componentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Component not found with ID: " + id));
        entity.setStockQuantity(stockQuantity);
        return ComponentMapper.toDto(componentRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteComponent(Long id) {
        Component entity = componentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Component not found with ID: " + id));
        componentRepository.delete(entity);
    }

    private void validateCommonFields(ComponentDto component) {
        if (component == null || component.getName() == null || component.getName().isBlank()
                || component.getBrand() == null || component.getBrand().isBlank()
                || component.getModel() == null || component.getModel().isBlank()) {
            throw new IllegalArgumentException("Component name, brand, and model are required.");
        }
        if (component.getPrice() == null || component.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Component price must be zero or greater.");
        }
        if (component.getStockQuantity() == null || component.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity must be zero or greater.");
        }
    }
}
