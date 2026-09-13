package com.pcbuilder.compatibility.service;

import com.pcbuilder.compatibility.dto.CompatibilityRequest;
import com.pcbuilder.compatibility.dto.CompatibilityResult;

public interface CompatibilityService {

    CompatibilityResult checkCompatibility(CompatibilityRequest request);
}
