package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.CompatibilityResult;

public interface CompatibilityRule {

    /**
     * Evaluates this specific compatibility or layout rule.
     * If an issue is discovered, appends a detailed warning or block
     * message directly to the shared, mutable result payload.
     *
     * @param context the read-only session context housing all resolved component specs
     * @param result the mutable results accumulator
     */
    void evaluate(CompatibilityContext context, CompatibilityResult result);
}
