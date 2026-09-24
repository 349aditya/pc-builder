package com.pcbuilder.build.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompatibilityMessageDto {
    private String ruleName;
    private String severity;
    private String description;
    private List<Long> affectedComponentIds = new ArrayList<>();
}
