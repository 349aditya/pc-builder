package com.pcbuilder.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CpuDto extends ComponentDto {
    private String socketType;
    private Integer coreCount;
    private Integer threadCount;
    private BigDecimal baseClockGhz;
    private BigDecimal boostClockGhz;
    private Integer tdpWatts;
    private Boolean hasIntegratedGraphics;
}