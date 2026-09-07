package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.CpuDto;
import com.pcbuilder.compatibility.dto.MotherboardDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SocketMatchingRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        CpuDto cpu = context.getCpu();
        MotherboardDto mobo = context.getMotherboard();

        if (cpu == null || mobo == null) {
            return;
        }

        if (!cpu.getSocketType().equalsIgnoreCase(mobo.getSocketType())) {
            String description = String.format(
                    "The CPU %s %s (Socket %s) is physically incompatible with the Motherboard %s %s (Socket %s).",
                    cpu.getBrand(), cpu.getModel(), cpu.getSocketType(),
                    mobo.getBrand(), mobo.getModel(), mobo.getSocketType()
            );

            result.addMessage(
                    "CPU_SOCKET_MATCH",
                    Severity.RED,
                    description,
                    List.of(cpu.getId(), mobo.getId())
            );
        }
    }
}