package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.GpuDto;
import com.pcbuilder.compatibility.dto.PcCaseDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GpuLengthClearanceRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        GpuDto gpu = context.getGpu();
        PcCaseDto pcCase = context.getPcCase();
//skip if GPU or Case has not been selected yet
        if (gpu == null || pcCase == null) {
            return;
        }

        if (gpu.getLengthMm() != null && pcCase.getMaxGpuLengthMm() != null) {
            if (gpu.getLengthMm() > pcCase.getMaxGpuLengthMm()) {
                String description = String.format(
                        "The Graphics Card %s %s length (%dmm) exceeds the maximum physical GPU length clearance of the selected Case %s %s (%dmm).",
                        gpu.getBrand(), gpu.getModel(), gpu.getLengthMm(),
                        pcCase.getBrand(), pcCase.getModel(), pcCase.getMaxGpuLengthMm()
                );

                result.addMessage(
                        "GPU_LENGTH_CLEARANCE",
                        Severity.RED,
                        description,
                        List.of(gpu.getId(), pcCase.getId())
                );
            }
        }
    }
}
