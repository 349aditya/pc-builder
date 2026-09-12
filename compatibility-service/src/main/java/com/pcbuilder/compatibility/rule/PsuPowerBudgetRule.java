package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PsuPowerBudgetRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        CpuDto cpu = context.getCpu();
        GpuDto gpu = context.getGpu();
        PowerSupplyDto psu = context.getPowerSupply();
        CpuCoolerDto cooler = context.getCooler();

        int estimatedWattage = 50;

        if (cpu != null && cpu.getTdpWatts() != null) {
            estimatedWattage += cpu.getTdpWatts();
        }
        if (gpu != null && gpu.getTdpWatts() != null) {
            estimatedWattage += gpu.getTdpWatts();
        }
        if (cooler != null && "LIQUID".equalsIgnoreCase(cooler.getCoolerType())) {
            estimatedWattage += 15;
        }

        for (CompatibilityContext.ResolvedStorage storage : context.getStorageSelections()) {
            estimatedWattage += storage.getQuantity() * 5;
        }

        for (CompatibilityContext.ResolvedCaseFan fan : context.getCaseFanSelections()) {
            estimatedWattage += fan.getQuantity() * 3;
        }

        result.setEstimatedWattage(estimatedWattage);

        if (psu == null || cpu == null || gpu == null || psu.getWattage() == null) {
            return;
        }

        if (psu.getWattage() < estimatedWattage) {
            String description = String.format(
                    "Selected Power Supply %s %s (%dW) is insufficient to support the estimated peak system load (%dW).",
                    psu.getBrand(), psu.getModel(), psu.getWattage(), estimatedWattage
            );

            result.addMessage(
                    "PSU_POWER_DEFICIT",
                    Severity.RED,
                    description,
                    List.of(psu.getId())
            );
        }
    }
}
