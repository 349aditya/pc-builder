package com.pcbuilder.compatibility.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GpuDto extends ComponentDto {
    private Integer lengthMm;
    private Integer tdpWatts;
    private Integer recommendedPsuWattage;
}