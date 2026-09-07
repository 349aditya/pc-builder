package com.pcbuilder.compatibility.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompatibilityMessage {
    private String ruleName;
    private Severity severity;
    private String description;
    private List<Long> affectedComponentIds;
}