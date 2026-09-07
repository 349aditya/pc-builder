package com.pcbuilder.compatibility.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseFanDto extends ComponentDto {
    private Integer fanSizeMm;
}