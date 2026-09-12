package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.GpuDto;
import com.pcbuilder.compatibility.dto.PowerSupplyDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PsuRecommendationRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        GpuDto gpu = context.getGpu();
        PowerSupplyDto psu = context.getPowerSupply();

        if (gpu == null || psu == null || gpu.getRecommendedPsuWattage() == null || psu.getWattage() == null) {
            return;
        }

        if (psu.getWattage() < gpu.getRecommendedPsuWattage()) {
            String description = String.format(
                    "GPU manufacturer recommends a minimum %dW Power Supply for %s %s. Selected PSU is %dW.",
                    gpu.getRecommendedPsuWattage(), gpu.getBrand(), gpu.getModel(), psu.getWattage()
            );

            result.addMessage(
                    "PSU_OVERHEAD_RECOMMENDATION",
                    Severity.YELLOW,
                    description,
                    List.of(gpu.getId(), psu.getId())
            );
        }
    }
}
