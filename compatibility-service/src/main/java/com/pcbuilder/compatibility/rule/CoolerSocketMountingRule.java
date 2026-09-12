package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.CpuCoolerDto;
import com.pcbuilder.compatibility.dto.CpuDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoolerSocketMountingRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        CpuDto cpu = context.getCpu();
        CpuCoolerDto cooler = context.getCooler();

        if (cpu == null || cooler == null || cpu.getSocketType() == null || cooler.getSupportedSockets() == null) {
            return;
        }

        boolean socketSupported = cooler.getSupportedSockets().stream()
                .anyMatch(s -> s.equalsIgnoreCase(cpu.getSocketType()));

        if (!socketSupported) {
            String description = String.format(
                    "The CPU Cooler %s %s does not support mounting on CPU socket type %s.",
                    cooler.getBrand(), cooler.getModel(), cpu.getSocketType()
            );

            result.addMessage(
                    "COOLER_SOCKET_SUPPORT",
                    Severity.RED,
                    description,
                    List.of(cpu.getId(), cooler.getId())
            );
        }
    }
}
