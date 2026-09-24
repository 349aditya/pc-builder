package com.pcbuilder.build.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BuildRequest {
    @NotBlank
    @Size(max = 120)
    private String name;
    private Long cpuId;
    private Long motherboardId;
    private Long gpuId;
    private Long coolerId;
    private Long psuId;
    private Long pcCaseId;
    @Valid
    private SelectionDto ramSelection;
    @Valid
    private List<SelectionDto> storageSelections = new ArrayList<>();
    @Valid
    private List<SelectionDto> caseFanSelections = new ArrayList<>();
}
