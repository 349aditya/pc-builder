package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PowerSupplyDto extends ComponentDto {
    private Integer wattage;
    private String efficiencyRating;
    private String formFactor;
    private Boolean isModular;
}