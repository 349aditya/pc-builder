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
