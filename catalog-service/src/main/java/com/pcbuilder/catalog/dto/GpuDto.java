package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GpuDto extends ComponentDto {
    private String chipset;
    private Integer vramGb;
    private Integer lengthMm;
    private Integer tdpWatts;
    private Integer recommendedPsuWattage;
}