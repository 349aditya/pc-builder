package com.pcbuilder.build.dto;

import java.util.List;

public record BulkLookupRequest(List<Long> componentIds) {
}
