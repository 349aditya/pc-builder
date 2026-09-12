package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.MotherboardDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SataPortsDepletedRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        MotherboardDto mobo = context.getMotherboard();
        List<CompatibilityContext.ResolvedStorage> storageList = context.getStorageSelections();

        if (mobo == null || mobo.getSataPorts() == null || storageList.isEmpty()) {
            return;
        }

        int sataCount = 0;
        List<Long> affectedSataStorageIds = new ArrayList<>();

        for (CompatibilityContext.ResolvedStorage storage : storageList) {
            if (storage.getDto() != null && storage.getDto().getFormFactor() != null) {
                String ff = storage.getDto().getFormFactor();
                if ("2.5\"".equals(ff) || "3.5\"".equals(ff) || "SATA".equalsIgnoreCase(ff)) {
                    sataCount += storage.getQuantity();
                    affectedSataStorageIds.add(storage.getDto().getId());
                }
            }
        }

        if (sataCount > mobo.getSataPorts()) {
            List<Long> affectedIds = new ArrayList<>();
            affectedIds.add(mobo.getId());
            affectedIds.addAll(affectedSataStorageIds);

            String description = String.format(
                "Selected %d SATA drive(s), but Motherboard %s %s only has %d SATA port(s).",
                sataCount, mobo.getBrand(), mobo.getModel(), mobo.getSataPorts()
            );

            result.addMessage(
                "SATA_PORTS_DEPLETED",
                Severity.RED,
                description,
                affectedIds
            );
        }
    }
}
