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
                                             BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
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
}
