package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.MotherboardDto;
import com.pcbuilder.compatibility.dto.PcCaseDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CaseFormFactorRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        MotherboardDto mobo = context.getMotherboard();
        PcCaseDto pcCase = context.getPcCase();

        //skip rule if either Motherboard or Case has not been selected yet
        if (mobo == null || pcCase == null) {
            return;
        }

        String formFactor = mobo.getFormFactor() != null ? mobo.getFormFactor().toUpperCase() : "";
        boolean supportsFormFactor = false;

        switch (formFactor) {
            case "ATX":
                supportsFormFactor = pcCase.getSupportsAtx() != null && pcCase.getSupportsAtx();
                break;
            case "MICRO-ATX", "MATX":
                supportsFormFactor = pcCase.getSupportsMicroAtx() != null && pcCase.getSupportsMicroAtx();
                break;
            case "MINI-ITX", "ITX":
                supportsFormFactor = pcCase.getSupportsMiniItx() != null && pcCase.getSupportsMiniItx();
                break;
            default:
                supportsFormFactor = false;
                break;
        }

        if (!supportsFormFactor) {
            String description = String.format(
                    "The Motherboard %s %s (Form Factor: %s) is physically too large to mount inside the selected Case %s %s.",
                    mobo.getBrand(), mobo.getModel(), mobo.getFormFactor(),
                    pcCase.getBrand(), pcCase.getModel()
            );

            result.addMessage(
                    "CASE_FORM_FACTOR_MATCH",
                    Severity.RED,
                    description,
                    List.of(mobo.getId(), pcCase.getId())
            );
        }
    }
}