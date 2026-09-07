package com.pcbuilder.compatibility.dto;

import java.util.List;

/**
 * Clean wrapper containing the flat array of component IDs
 * sent to catalog-service for bulk hydration.
 */
public record BulkLookupRequest(List<Long> componentIds) {}