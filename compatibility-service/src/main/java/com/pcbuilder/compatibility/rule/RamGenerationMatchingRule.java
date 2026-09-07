package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.MotherboardDto;
import com.pcbuilder.compatibility.dto.RamDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RamGenerationMatchingRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        MotherboardDto mobo = context.getMotherboard();
        RamDto ram = context.getRam();

        if (mobo == null || ram == null) {
            return;
        }

        if (!mobo.getRamType().equalsIgnoreCase(ram.getRamType())) {
            String description = String.format(
                    "The Motherboard %s %s supports %s RAM, but the selected memory is %s.",
                    mobo.getBrand(), mobo.getModel(), mobo.getRamType().toUpperCase(),
                    ram.getRamType().toUpperCase()
            );

            result.addMessage(
                    "RAM_GENERATION_MATCH",
                    Severity.RED,
                    description,
                    List.of(mobo.getId(), ram.getId())
            );
        }
    }
}