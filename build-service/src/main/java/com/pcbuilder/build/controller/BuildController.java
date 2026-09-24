package com.pcbuilder.build.controller;

import com.pcbuilder.build.dto.BuildRequest;
import com.pcbuilder.build.dto.BuildResponse;
import com.pcbuilder.build.service.BuildService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/builds")
public class BuildController {
    private final BuildService buildService;

    public BuildController(BuildService buildService) {
        this.buildService = buildService;
    }

    @PostMapping
    public ResponseEntity<BuildResponse> create(@Valid @RequestBody BuildRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildService.create(request));
    }

    @GetMapping
    public List<BuildResponse> getAll() {
        return buildService.getAll();
    }

    @GetMapping("/{id}")
    public BuildResponse getById(@PathVariable Long id) {
        return buildService.getById(id);
    }

    @PutMapping("/{id}")
    public BuildResponse update(@PathVariable Long id, @Valid @RequestBody BuildRequest request) {
        return buildService.update(id, request);
    }

    @PostMapping("/{id}/validate")
    public BuildResponse validate(@PathVariable Long id) {
        return buildService.validate(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        buildService.delete(id);
    }
}
