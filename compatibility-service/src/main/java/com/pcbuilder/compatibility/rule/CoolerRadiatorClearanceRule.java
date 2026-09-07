package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.CpuCoolerDto;
import com.pcbuilder.compatibility.dto.PcCaseDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoolerRadiatorClearanceRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        CpuCoolerDto cooler = context.getCooler();
        PcCaseDto pcCase = context.getPcCase();

        //skip if Cooler or Case is absent
        if (cooler == null || pcCase == null) {
            return;
        }

        // Only evaluate if the cooler is explicitly configured as a LIQUID AIO cooler
        String coolerType = cooler.getCoolerType() != null ? cooler.getCoolerType().toUpperCase() : "";
        if (!"LIQUID".equals(coolerType)) {
            return;
        }

        if (cooler.getRadiatorSizeMm() != null && pcCase.getMaxRadiatorSizeMm() != null) {
            // Error if case does not support liquid mounting, or if exceeds case maximum capacity
            if (pcCase.getMaxRadiatorSizeMm() == 0 || cooler.getRadiatorSizeMm() > pcCase.getMaxRadiatorSizeMm()) {
                String description = String.format(
                        "The CPU Liquid Cooler %s %s radiator size (%dmm) exceeds the maximum radiator mounting bracket size of the selected Case %s %s (%dmm).",
                        cooler.getBrand(), cooler.getModel(), cooler.getRadiatorSizeMm(),
                        pcCase.getBrand(), pcCase.getModel(), pcCase.getMaxRadiatorSizeMm()
                );

                result.addMessage(
                        "COOLER_RADIATOR_CLEARANCE",
                        Severity.RED,
                        description,
                        List.of(cooler.getId(), pcCase.getId())
                );
            }
        }
    }
}