package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MotherboardDto extends ComponentDto {
    private String socketType;
    private String formFactor;
    private String chipset;
    private String ramType;
    private Integer ramSlots;
    private Integer maxRamCapacityGb;
    private Integer m2Slots;
    private Integer sataPorts;
}
