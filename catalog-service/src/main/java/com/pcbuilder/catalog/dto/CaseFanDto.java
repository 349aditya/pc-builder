package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseFanDto extends ComponentDto {
    private Integer fanSizeMm;
    private Double airflowCfm;
    private Double noiseDb;
    private Boolean isRgb;
}