package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RamDto extends ComponentDto {
    private String ramType;
    private Integer capacityGb;
    private Integer stickCount;
    private Integer speedMhz;
    private Integer casLatency;
}