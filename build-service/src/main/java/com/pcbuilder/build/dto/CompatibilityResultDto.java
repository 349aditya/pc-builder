package com.pcbuilder.build.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompatibilityResultDto {
    private boolean compatible = true;
    private Integer estimatedWattage = 0;
    private List<CompatibilityMessageDto> messages = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();
}
