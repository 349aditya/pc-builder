package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PcCaseDto extends ComponentDto {
    private Integer maxGpuLengthMm;
    private Integer maxCpuCoolerHeightMm;
    private Integer maxRadiatorSizeMm;
    private Boolean supportsAtx;
    private Boolean supportsMicroAtx;
    private Boolean supportsMiniItx;
    private Integer max120mmFans;
    private Integer max140mmFans;
}
