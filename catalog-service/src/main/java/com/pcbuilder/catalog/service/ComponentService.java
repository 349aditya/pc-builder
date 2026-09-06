package com.pcbuilder.catalog.service;

import com.pcbuilder.catalog.entity.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComponentService {

    Page<Component> getAllComponents(Pageable pageable);

    Component getComponentById(Long id);

    List<Component> searchComponents(String keyword);

    List<Component> getComponentsByBrand(String brand);

    List<Component> getLowStockComponents(Integer threshold);
}
