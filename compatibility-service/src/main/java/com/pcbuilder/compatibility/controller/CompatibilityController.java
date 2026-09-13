package com.pcbuilder.compatibility.controller;

import com.pcbuilder.compatibility.dto.CompatibilityRequest;
import com.pcbuilder.compatibility.dto.CompatibilityResult;
import com.pcbuilder.compatibility.service.CompatibilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/compatibility")
public class CompatibilityController {

    private final CompatibilityService compatibilityService;

    public CompatibilityController(CompatibilityService compatibilityService) {
        this.compatibilityService = compatibilityService;
    }

    @PostMapping("/check")
    public ResponseEntity<CompatibilityResult> checkCompatibility(@Valid @RequestBody CompatibilityRequest request) {
        CompatibilityResult result = compatibilityService.checkCompatibility(request);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "Bad Request",
                "message", ex.getMessage(),
                "status", HttpStatus.BAD_REQUEST.value()
        ));
    }
}
