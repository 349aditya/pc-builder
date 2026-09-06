package com.pcbuilder.catalog.repository;

import com.pcbuilder.catalog.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {


    List<Component> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(String name, String brand);

    List<Component> findByBrandIgnoreCase(String brand);

    List<Component> findByStockQuantityLessThan(Integer threshold);

}

