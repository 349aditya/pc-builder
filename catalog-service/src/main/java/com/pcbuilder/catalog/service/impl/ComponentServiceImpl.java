package com.pcbuilder.catalog.service.impl;

import com.pcbuilder.catalog.entity.Component;
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
    public Page<Component> getAllComponents(Pageable pageable) {
        return componentRepository.findAll(pageable);
    }

    @Override
    public Component getComponentById(Long id) {
        return componentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Component not found with ID: " + id));
    }

    @Override
    public List<Component> searchComponents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }
        return componentRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Component> getComponentsByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            return List.of();
        }
        return componentRepository.findByBrandIgnoreCase(brand);
    }

    @Override
    public List<Component> getLowStockComponents(Integer threshold) {
        int limit = (threshold != null) ? threshold : 5;
        return componentRepository.findByStockQuantityLessThan(limit);
    }
}
