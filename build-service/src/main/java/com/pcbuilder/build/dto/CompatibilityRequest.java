package com.pcbuilder.build.dto;

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
    private SelectionDto ramSelection;
    private List<SelectionDto> storageSelections = new ArrayList<>();
    private List<SelectionDto> caseFanSelections = new ArrayList<>();
}
