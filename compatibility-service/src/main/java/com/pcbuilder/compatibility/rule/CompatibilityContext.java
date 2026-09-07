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
            this.cpu = (CpuDto) dtoMap.get(request.getCpuId());
        }
        if (request.getMotherboardId() != null) {
            this.motherboard = (MotherboardDto) dtoMap.get(request.getMotherboardId());
        }
        if (request.getGpuId() != null) {
            this.gpu = (GpuDto) dtoMap.get(request.getGpuId());
        }
        if (request.getCoolerId() != null) {
            this.cooler = (CpuCoolerDto) dtoMap.get(request.getCoolerId());
        }
        if (request.getPsuId() != null) {
            this.powerSupply = (PowerSupplyDto) dtoMap.get(request.getPsuId());
        }
        if (request.getPcCaseId() != null) {
            this.pcCase = (PcCaseDto) dtoMap.get(request.getPcCaseId());
        }

        if (request.getRamSelection() != null && request.getRamSelection().getComponentId() != null) {
            this.ram = (RamDto) dtoMap.get(request.getRamSelection().getComponentId());
            this.ramQuantity = request.getRamSelection().getQuantity() != null ? request.getRamSelection().getQuantity() : 1;
        } else {
            this.ramQuantity = 0;
        }

        if (request.getStorageSelections() != null) {
            for (Selection sel : request.getStorageSelections()) {
                if (sel.getComponentId() != null) {
                    StorageDto storageDto = (StorageDto) dtoMap.get(sel.getComponentId());
                    if (storageDto != null) {
                        int qty = sel.getQuantity() != null ? sel.getQuantity() : 1;
                        this.storageSelections.add(new ResolvedStorage(storageDto, qty));
                    }
                }
            }
        }

        if (request.getCaseFanSelections() != null) {
            for (Selection sel : request.getCaseFanSelections()) {
                if (sel.getComponentId() != null) {
                    CaseFanDto fanDto = (CaseFanDto) dtoMap.get(sel.getComponentId());
                    if (fanDto != null) {
                        int qty = sel.getQuantity() != null ? sel.getQuantity() : 1;
                        this.caseFanSelections.add(new ResolvedCaseFan(fanDto, qty));
                    }
                }
            }
        }
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
