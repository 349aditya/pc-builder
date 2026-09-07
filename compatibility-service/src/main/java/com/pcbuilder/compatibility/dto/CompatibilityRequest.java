package com.pcbuilder.compatibility.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompatibilityRequest {
    private Long cpuId;
    private Long motherboardId;
    private Long gpuId;
    private Long coolerId;
    private Long psuId;
    private Long pcCaseId;

    @Valid
    private Selection ramSelection;

    @Valid
    private List<Selection> storageSelections = new ArrayList<>();

    @Valid
    private List<Selection> caseFanSelections = new ArrayList<>();
}