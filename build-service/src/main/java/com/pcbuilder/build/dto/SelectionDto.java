package com.pcbuilder.build.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SelectionDto {
    @NotNull
    @Positive
    private Long componentId;

    @NotNull
    @Positive
    @Max(100)
    private Integer quantity;
}
