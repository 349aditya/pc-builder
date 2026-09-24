package com.pcbuilder.build.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BuildItemDto {
    private Long componentId;
    private String categoryType;
    private String name;
    private String brand;
    private String model;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}
