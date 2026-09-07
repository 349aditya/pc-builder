package com.pcbuilder.compatibility.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CpuDto extends ComponentDto {
    private String socketType;
    private Integer tdpWatts;
}