package com.pcbuilder.catalog.repository;

import com.pcbuilder.catalog.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long>, JpaSpecificationExecutor<Component> {


    @Query(value = "SELECT * FROM components WHERE category_type = :category", countQuery = "SELECT COUNT(*) FROM components WHERE category_type = :category", nativeQuery = true)
    Page<Component> findByCategory(@Param("category") String category, Pageable pageable);

    List<Component> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(String name, String brand);

    List<Component> findByBrandIgnoreCase(String brand);

    List<Component> findByStockQuantityLessThan(Integer threshold);

}
