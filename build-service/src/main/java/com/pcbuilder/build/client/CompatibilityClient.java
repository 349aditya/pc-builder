package com.pcbuilder.build.client;

import com.pcbuilder.build.dto.CompatibilityRequest;
import com.pcbuilder.build.dto.CompatibilityResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "build-compatibility-client", url = "${app.compatibility-service.url:http://localhost:8081}")
public interface CompatibilityClient {
    @PostMapping("/api/compatibility/check")
    CompatibilityResultDto checkCompatibility(@RequestBody CompatibilityRequest request);
}
