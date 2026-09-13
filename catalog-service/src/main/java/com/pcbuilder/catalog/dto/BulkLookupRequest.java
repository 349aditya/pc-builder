package com.pcbuilder.catalog.dto;

import java.util.List;

public record BulkLookupRequest(List<Long> componentIds) {}
