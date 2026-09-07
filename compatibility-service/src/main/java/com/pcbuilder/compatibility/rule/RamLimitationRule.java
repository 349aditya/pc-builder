package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.MotherboardDto;
import com.pcbuilder.compatibility.dto.RamDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RamLimitationRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        MotherboardDto mobo = context.getMotherboard();
        RamDto ram = context.getRam();
        int quantity = context.getRamQuantity();

        if (mobo == null || ram == null || quantity <= 0) {
            return;
        }

        int totalSticks = ram.getStickCount() * quantity;
        if (totalSticks > mobo.getRamSlots()) {
            String description = String.format(
                    "Selected RAM configuration requires %d physical DIMM slots (%d kits of %dx sticks), but Motherboard %s %s only has %d physical slots.",
                    totalSticks, quantity, ram.getStickCount(),
                    mobo.getBrand(), mobo.getModel(), mobo.getRamSlots()
            );

            result.addMessage(
                    "RAM_SLOT_AND_CAPACITY_LIMIT",
                    Severity.RED,
                    description,
                    List.of(mobo.getId(), ram.getId())
            );
            return;
        }

        int totalCapacity = ram.getCapacityGb() * quantity;
        if (totalCapacity > mobo.getMaxRamCapacityGb()) {
            String description = String.format(
                    "Total configured RAM capacity (%d GB) exceeds the maximum supported capacity (%d GB) of the Motherboard %s %s.",
                    totalCapacity, mobo.getMaxRamCapacityGb(),
                    mobo.getBrand(), mobo.getModel()
            );

            result.addMessage(
                    "RAM_SLOT_AND_CAPACITY_LIMIT",
                    Severity.RED,
                    description,
                    List.of(mobo.getId(), ram.getId())
            );
        }
    }
}
