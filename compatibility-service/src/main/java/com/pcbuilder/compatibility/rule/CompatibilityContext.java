package com.pcbuilder.compatibility.rule;

import com.pcbuilder.compatibility.dto.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CompatibilityContext {

    private final CompatibilityRequest request;

    private CpuDto cpu;
    private MotherboardDto motherboard;
    private GpuDto gpu;
    private CpuCoolerDto cooler;
    private PowerSupplyDto powerSupply;
    private PcCaseDto pcCase;

    private RamDto ram;
    private final int ramQuantity;
    private final List<ResolvedStorage> storageSelections = new ArrayList<>();
    private final List<ResolvedCaseFan> caseFanSelections = new ArrayList<>();

    public CompatibilityContext(CompatibilityRequest request, List<ComponentDto> resolvedDtos) {
        this.request = request;

        Map<Long, ComponentDto> dtoMap = new HashMap<>();
        for (ComponentDto dto : resolvedDtos) {
            if (dto != null && dto.getId() != null) {
                dtoMap.put(dto.getId(), dto);
            }
        }

        if (request.getCpuId() != null) {
            this.cpu = resolve(dtoMap, request.getCpuId(), CpuDto.class);
        }
        if (request.getMotherboardId() != null) {
            this.motherboard = resolve(dtoMap, request.getMotherboardId(), MotherboardDto.class);
        }
        if (request.getGpuId() != null) {
            this.gpu = resolve(dtoMap, request.getGpuId(), GpuDto.class);
        }
        if (request.getCoolerId() != null) {
            this.cooler = resolve(dtoMap, request.getCoolerId(), CpuCoolerDto.class);
        }
        if (request.getPsuId() != null) {
            this.powerSupply = resolve(dtoMap, request.getPsuId(), PowerSupplyDto.class);
        }
        if (request.getPcCaseId() != null) {
            this.pcCase = resolve(dtoMap, request.getPcCaseId(), PcCaseDto.class);
        }

        if (request.getRamSelection() != null && request.getRamSelection().getComponentId() != null) {
            this.ram = resolve(dtoMap, request.getRamSelection().getComponentId(), RamDto.class);
            this.ramQuantity = request.getRamSelection().getQuantity() != null ? request.getRamSelection().getQuantity() : 1;
        } else {
            this.ramQuantity = 0;
        }

        if (request.getStorageSelections() != null) {
            for (Selection sel : request.getStorageSelections()) {
                if (sel != null && sel.getComponentId() != null) {
                    StorageDto storageDto = resolve(dtoMap, sel.getComponentId(), StorageDto.class);
                    if (storageDto != null) {
                        int qty = sel.getQuantity() != null ? sel.getQuantity() : 1;
                        this.storageSelections.add(new ResolvedStorage(storageDto, qty));
                    }
                }
            }
        }

        if (request.getCaseFanSelections() != null) {
            for (Selection sel : request.getCaseFanSelections()) {
                if (sel != null && sel.getComponentId() != null) {
                    CaseFanDto fanDto = resolve(dtoMap, sel.getComponentId(), CaseFanDto.class);
                    if (fanDto != null) {
                        int qty = sel.getQuantity() != null ? sel.getQuantity() : 1;
                        this.caseFanSelections.add(new ResolvedCaseFan(fanDto, qty));
                    }
                }
            }
        }
    }

    private static <T extends ComponentDto> T resolve(Map<Long, ComponentDto> components, Long id, Class<T> type) {
        ComponentDto component = components.get(id);
        if (!type.isInstance(component)) {
            throw new IllegalArgumentException("Component " + id + " must be of type " + type.getSimpleName().replace("Dto", "") + ".");
        }
        return type.cast(component);
    }

    @Getter
    public static class ResolvedStorage {
        private final StorageDto dto;
        private final int quantity;

        public ResolvedStorage(StorageDto dto, int quantity) {
            this.dto = dto;
            this.quantity = quantity;
        }
    }

    @Getter
    public static class ResolvedCaseFan {
        private final CaseFanDto dto;
        private final int quantity;

        public ResolvedCaseFan(CaseFanDto dto, int quantity) {
            this.dto = dto;
            this.quantity = quantity;
        }
    }
}
