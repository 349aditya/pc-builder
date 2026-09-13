package com.pcbuilder.catalog.service;

import com.pcbuilder.catalog.dto.ComponentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComponentService {

    Page<ComponentDto> getAllComponents(Pageable pageable);

    ComponentDto getComponentById(Long id);

    List<ComponentDto> searchComponents(String keyword);

    List<ComponentDto> getComponentsByBrand(String brand);

    List<ComponentDto> getLowStockComponents(Integer threshold);

    List<ComponentDto> getComponentsBulk(List<Long> componentIds);
}
