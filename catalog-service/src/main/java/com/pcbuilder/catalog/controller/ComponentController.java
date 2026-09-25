package com.pcbuilder.catalog.controller;

import com.pcbuilder.catalog.dto.BulkLookupRequest;
import com.pcbuilder.catalog.dto.ComponentDto;
import com.pcbuilder.catalog.dto.StockUpdateRequest;
import com.pcbuilder.catalog.service.ComponentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.math.BigDecimal;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public ResponseEntity<Page<ComponentDto>> getAllComponents(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String socketType,
            @RequestParam(required = false) String ramType,
            @RequestParam(required = false) Integer minWattage,
            @RequestParam(required = false) Integer maxLengthMm,
            @PageableDefault(size = 12, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(componentService.findComponents(
                category, brand, keyword, minPrice, maxPrice, socketType,
                ramType, minWattage, maxLengthMm, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentDto> getComponentById(@PathVariable Long id) {
        return ResponseEntity.ok(componentService.getComponentById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ComponentDto>> searchComponents(@RequestParam String keyword) {
        return ResponseEntity.ok(componentService.searchComponents(keyword));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ComponentDto>> getComponentsByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(componentService.getComponentsByBrand(brand));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ComponentDto>> getLowStockComponents(
            @RequestParam(required = false) Integer threshold) {
        return ResponseEntity.ok(componentService.getLowStockComponents(threshold));
    }


    @PostMapping("/bulk")
    public ResponseEntity<List<ComponentDto>> getComponentsBulk(@RequestBody BulkLookupRequest request) {
        List<Long> ids = (request != null && request.componentIds() != null)
                ? request.componentIds()
                : List.of();
        return ResponseEntity.ok(componentService.getComponentsBulk(ids));
    }

    @PostMapping
    public ResponseEntity<ComponentDto> createComponent(@Valid @RequestBody ComponentDto component) {
        return ResponseEntity.status(HttpStatus.CREATED).body(componentService.createComponent(component));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentDto> updateComponent(
            @PathVariable Long id, @Valid @RequestBody ComponentDto component) {
        return ResponseEntity.ok(componentService.updateComponent(id, component));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ComponentDto> updateStock(
            @PathVariable Long id, @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(componentService.updateStock(id, request.stockQuantity()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Not Found",
                "message", ex.getMessage(),
                "status", HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Bad Request",
                "message", ex.getMessage(),
                "status", HttpStatus.BAD_REQUEST.value()
        ));
    }
}
