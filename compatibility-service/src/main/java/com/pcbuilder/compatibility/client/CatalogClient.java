package com.pcbuilder.compatibility.client;

import com.pcbuilder.compatibility.dto.BulkLookupRequest;
import com.pcbuilder.compatibility.dto.ComponentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * OpenFeign client mapping to catalog-service REST endpoints.
 * Uses a configurable property fallback to http://localhost:8080.
 * This allows you to run the services locally side-by-side immediately,
 * while remaining 100% cloud-ready for Eureka or Kubernetes DNS in production!
 */
@FeignClient(
        name = "catalog-service",
        url = "${app.catalog-service.url:http://localhost:8080}"
)
public interface CatalogClient {

    /**
     * Synchronously POSTs a collection of IDs to catalog-service and returns
     * polymorphically inflated component specification DTOs.
     */
    @PostMapping("/api/components/bulk")
    List<ComponentDto> getComponentsBulk(@RequestBody BulkLookupRequest request);
}