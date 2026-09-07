package com.pcbuilder.compatibility.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompatibilityResult {
    private boolean compatible = true;
    private Integer estimatedWattage = 0;
    private List<CompatibilityMessage> messages = new ArrayList<>();

    public void addMessage(String ruleName, Severity severity, String description, List<Long> affectedComponentIds) {
        this.messages.add(new CompatibilityMessage(ruleName, severity, description, affectedComponentIds));
        if (severity == Severity.RED) {
            this.compatible = false;
        }
    }
}