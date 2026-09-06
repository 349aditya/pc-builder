package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StorageDto extends ComponentDto {
    private String storageType;
    private String formFactor;
    private String interfaceType;
    private Integer capacityGb;
    private Integer readSpeedMbps;
    private Integer writeSpeedMbps;
}