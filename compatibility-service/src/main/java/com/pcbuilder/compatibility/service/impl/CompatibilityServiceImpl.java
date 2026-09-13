package com.pcbuilder.compatibility.service.impl;

import com.pcbuilder.compatibility.client.CatalogClient;
import com.pcbuilder.compatibility.dto.*;
import com.pcbuilder.compatibility.rule.CompatibilityContext;
import com.pcbuilder.compatibility.rule.CompatibilityRule;
import com.pcbuilder.compatibility.service.CompatibilityService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompatibilityServiceImpl implements CompatibilityService {

    private final CatalogClient catalogClient;
    private final List<CompatibilityRule> rules;

    public CompatibilityServiceImpl(CatalogClient catalogClient, List<CompatibilityRule> rules) {
        this.catalogClient = catalogClient;
        this.rules = rules;
    }

    @Override
    public CompatibilityResult checkCompatibility(CompatibilityRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Compatibility request cannot be null.");
        }

        Set<Long> requestedIdSet = extractComponentIds(request);

        List<ComponentDto> resolvedDtos = Collections.emptyList();

        if (!requestedIdSet.isEmpty()) {
            List<Long> requestedIds = new ArrayList<>(requestedIdSet);

            resolvedDtos = catalogClient.getComponentsBulk(new BulkLookupRequest(requestedIds));

            if (resolvedDtos == null) {
                resolvedDtos = Collections.emptyList();
            }

            Set<Long> fetchedIds = resolvedDtos.stream()
                    .map(ComponentDto::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Set<Long> missingIds = requestedIds.stream()
                    .filter(id -> !fetchedIds.contains(id))
                    .collect(Collectors.toSet());

            if (!missingIds.isEmpty()) {
                throw new IllegalArgumentException(
                        "Component resolution failed. The following ID(s) do not exist in the catalog: " + missingIds
                );
            }
        }

        CompatibilityContext context = new CompatibilityContext(request, resolvedDtos);

        CompatibilityResult result = new CompatibilityResult();

        for (CompatibilityRule rule : rules) {
            rule.evaluate(context, result);
        }

        return result;
    }

    private Set<Long> extractComponentIds(CompatibilityRequest request) {
        Set<Long> ids = new LinkedHashSet<>();

        if (request.getCpuId() != null) ids.add(request.getCpuId());
        if (request.getMotherboardId() != null) ids.add(request.getMotherboardId());
        if (request.getGpuId() != null) ids.add(request.getGpuId());
        if (request.getCoolerId() != null) ids.add(request.getCoolerId());
        if (request.getPsuId() != null) ids.add(request.getPsuId());
        if (request.getPcCaseId() != null) ids.add(request.getPcCaseId());

        if (request.getRamSelection() != null && request.getRamSelection().getComponentId() != null) {
            ids.add(request.getRamSelection().getComponentId());
        }

        if (request.getStorageSelections() != null) {
            for (Selection selection : request.getStorageSelections()) {
                if (selection != null && selection.getComponentId() != null) {
                    ids.add(selection.getComponentId());
                }
            }
        }

        if (request.getCaseFanSelections() != null) {
            for (Selection selection : request.getCaseFanSelections()) {
                if (selection != null && selection.getComponentId() != null) {
                    ids.add(selection.getComponentId());
                }
            }
        }

        return ids;
    }
}
