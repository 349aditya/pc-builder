package com.pcbuilder.catalog.controller;

import com.pcbuilder.catalog.entity.Component;
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

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }


    @GetMapping
    public ResponseEntity<Page<Component>> getAllComponents(
            @PageableDefault(size = 12, sort = "name") Pageable pageable) {
        Page<Component> components = componentService.getAllComponents(pageable);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable Long id) {
        Component component = componentService.getComponentById(id);
        return ResponseEntity.ok(component);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Component>> searchComponents(@RequestParam String keyword) {
        List<Component> results = componentService.searchComponents(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Component>> getComponentsByBrand(@PathVariable String brand) {
        List<Component> results = componentService.getComponentsByBrand(brand);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Component>> getLowStockComponents(
            @RequestParam(required = false) Integer threshold) {
        List<Component> results = componentService.getLowStockComponents(threshold);
        return ResponseEntity.ok(results);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Not Found",
                "message", ex.getMessage(),
                "status", HttpStatus.NOT_FOUND.value()
        ));
    }
}