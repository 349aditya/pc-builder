package com.pcbuilder.compatibility.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class CpuCoolerDto extends ComponentDto {
    private String coolerType;
    private Integer heightMm;
    private Integer radiatorSizeMm;
    private Set<String> supportedSockets;
}