package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.MotherboardDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class M2SlotsDepletedRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        MotherboardDto mobo = context.getMotherboard();
        List<CompatibilityContext.ResolvedStorage> storageList = context.getStorageSelections();

        if (mobo == null || mobo.getM2Slots() == null || storageList.isEmpty()) {
            return;
        }

        int m2Count = 0;
        List<Long> affectedM2StorageIds = new ArrayList<>();

        for (CompatibilityContext.ResolvedStorage storage : storageList) {
            if (storage.getDto() != null && "M.2".equalsIgnoreCase(storage.getDto().getFormFactor())) {
                m2Count += storage.getQuantity();
                affectedM2StorageIds.add(storage.getDto().getId());
            }
        }

        if (m2Count > mobo.getM2Slots()) {
            List<Long> affectedIds = new ArrayList<>();
            affectedIds.add(mobo.getId());
            affectedIds.addAll(affectedM2StorageIds);

            String description = String.format(
                "Selected %d M.2 NVMe drive(s), but Motherboard %s %s only has %d M.2 slot(s).",
                m2Count, mobo.getBrand(), mobo.getModel(), mobo.getM2Slots()
            );

            result.addMessage(
                "M2_SLOTS_DEPLETED",
                Severity.RED,
                description,
                affectedIds
            );
        }
    }
}
