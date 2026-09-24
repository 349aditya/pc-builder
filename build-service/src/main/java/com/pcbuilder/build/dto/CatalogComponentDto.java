package com.pcbuilder.build.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CatalogComponentDto {
    private Long id;
    private String categoryType;
    private String name;
    private String brand;
    private String model;
    private BigDecimal price;
    private Integer stockQuantity;
}
