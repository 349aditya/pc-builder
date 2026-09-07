package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.CpuCoolerDto;
import com.pcbuilder.compatibility.dto.PcCaseDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoolerHeightClearanceRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        CpuCoolerDto cooler = context.getCooler();
        PcCaseDto pcCase = context.getPcCase();

        // skip if cooler or case is absent
        if (cooler == null || pcCase == null) {
            return;
        }

        // evaluate if the cooler is configured as an air cooler
        String coolerType = cooler.getCoolerType() != null ? cooler.getCoolerType().toUpperCase() : "";
        if (!"AIR".equals(coolerType)) {
            return;
        }

        if (cooler.getHeightMm() != null && pcCase.getMaxCpuCoolerHeightMm() != null) {
            if (cooler.getHeightMm() > pcCase.getMaxCpuCoolerHeightMm()) {
                String description = String.format(
                        "The CPU Air Cooler %s %s height (%dmm) is too tall for the selected Case %s %s side-panel clearance (%dmm).",
                        cooler.getBrand(), cooler.getModel(), cooler.getHeightMm(),
                        pcCase.getBrand(), pcCase.getModel(), pcCase.getMaxCpuCoolerHeightMm()
                );

                result.addMessage(
                        "COOLER_HEIGHT_CLEARANCE",
                        Severity.RED,
                        description,
                        List.of(cooler.getId(), pcCase.getId())
                );
            }
        }
    }
}