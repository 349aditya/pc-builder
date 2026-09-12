package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.dto.PcCaseDto;
import com.pcbuilder.compatibility.dto.Severity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CaseFanLimitsExceededRule implements CompatibilityRule {

    @Override
    public void evaluate(CompatibilityContext context, CompatibilityResult result) {
        PcCaseDto pcCase = context.getPcCase();
        List<CompatibilityContext.ResolvedCaseFan> fanList = context.getCaseFanSelections();

        if (pcCase == null || fanList.isEmpty()) {
            return;
        }

        int total120mm = 0;
        int total140mm = 0;
        List<Long> affected120mmFanIds = new ArrayList<>();
        List<Long> affected140mmFanIds = new ArrayList<>();

        for (CompatibilityContext.ResolvedCaseFan fan : fanList) {
            if (fan.getDto() != null && fan.getDto().getFanSizeMm() != null) {
                if (fan.getDto().getFanSizeMm() == 120) {
                    total120mm += fan.getQuantity();
                    affected120mmFanIds.add(fan.getDto().getId());
                } else if (fan.getDto().getFanSizeMm() == 140) {
                    total140mm += fan.getQuantity();
                    affected140mmFanIds.add(fan.getDto().getId());
                }
            }
        }

        if (pcCase.getMax120mmFans() != null && total120mm > pcCase.getMax120mmFans()) {
            List<Long> affectedIds = new ArrayList<>();
            affectedIds.add(pcCase.getId());
            affectedIds.addAll(affected120mmFanIds);

            String description = String.format(
                "Selected %d active 120mm fans, but Case %s %s only supports a maximum of %d.",
                total120mm, pcCase.getBrand(), pcCase.getModel(), pcCase.getMax120mmFans()
            );

            result.addMessage(
                "CASE_FAN_LIMITS_EXCEEDED",
                Severity.RED,
                description,
                affectedIds
            );
        }

        if (pcCase.getMax140mmFans() != null && total140mm > pcCase.getMax140mmFans()) {
            List<Long> affectedIds = new ArrayList<>();
            affectedIds.add(pcCase.getId());
            affectedIds.addAll(affected140mmFanIds);

            String description = String.format(
                "Selected %d active 140mm fans, but Case %s %s only supports a maximum of %d.",
                total140mm, pcCase.getBrand(), pcCase.getModel(), pcCase.getMax140mmFans()
            );

            result.addMessage(
                "CASE_FAN_LIMITS_EXCEEDED",
                Severity.RED,
                description,
                affectedIds
            );
        }
    }
}
