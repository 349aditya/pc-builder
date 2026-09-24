package com.pcbuilder.build.client;

import com.pcbuilder.build.dto.BulkLookupRequest;
import com.pcbuilder.build.dto.CatalogComponentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "build-catalog-client", url = "${app.catalog-service.url:http://localhost:8080}")
public interface CatalogClient {
    @PostMapping("/api/components/bulk")
    List<CatalogComponentDto> getComponentsBulk(@RequestBody BulkLookupRequest request);
}
