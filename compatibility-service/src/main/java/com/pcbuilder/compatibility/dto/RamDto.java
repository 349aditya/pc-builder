package com.pcbuilder.compatibility.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RamDto extends ComponentDto {
    private String ramType;
    private Integer stickCount;
    private Integer capacityGb;
}