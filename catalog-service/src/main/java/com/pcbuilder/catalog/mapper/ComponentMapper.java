package com.pcbuilder.catalog.mapper;

import com.pcbuilder.catalog.dto.*;
import com.pcbuilder.catalog.entity.*;

public class ComponentMapper {

    public static ComponentDto toDto(Component entity) {
        if (entity == null) {
            return null;
        }

        ComponentDto dto = switch (entity) {
            case Cpu cpu -> mapCpu(cpu);
            case Motherboard mobo -> mapMotherboard(mobo);
            case Ram ram -> mapRam(ram);
            case Gpu gpu -> mapGpu(gpu);
            case PcCase pcCase -> mapPcCase(pcCase);
            case PowerSupply psu -> mapPowerSupply(psu);
            case Storage storage -> mapStorage(storage);
            case CpuCooler cooler -> mapCpuCooler(cooler);
            case CaseFan fan -> mapCaseFan(fan);
            default -> throw new IllegalArgumentException("Unknown entity subclass mapping: " + entity.getClass().getName());
        };

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setPrice(entity.getPrice());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setImageUrl(entity.getImageUrl());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    private static CpuDto mapCpu(Cpu entity) {
        CpuDto dto = new CpuDto();
        dto.setSocketType(entity.getSocketType());
        dto.setCoreCount(entity.getCoreCount());
        dto.setThreadCount(entity.getThreadCount());
        dto.setBaseClockGhz(entity.getBaseClockGhz());
        dto.setBoostClockGhz(entity.getBoostClockGhz());
        dto.setTdpWatts(entity.getTdpWatts());
        dto.setHasIntegratedGraphics(entity.getHasIntegratedGraphics());
        return dto;
    }

    private static MotherboardDto mapMotherboard(Motherboard entity) {
        MotherboardDto dto = new MotherboardDto();
        dto.setSocketType(entity.getSocketType());
        dto.setFormFactor(entity.getFormFactor());
        dto.setChipset(entity.getChipset());
        dto.setRamType(entity.getRamType());
        dto.setRamSlots(entity.getRamSlots());
        dto.setMaxRamCapacityGb(entity.getMaxRamCapacityGb());
        dto.setM2Slots(entity.getM2Slots());
        dto.setSataPorts(entity.getSataPorts());
        return dto;
    }

    private static RamDto mapRam(Ram entity) {
        RamDto dto = new RamDto();
        dto.setRamType(entity.getRamType());
        dto.setCapacityGb(entity.getCapacityGb());
        dto.setStickCount(entity.getStickCount());
        dto.setSpeedMhz(entity.getSpeedMhz());
        dto.setCasLatency(entity.getCasLatency());
        return dto;
    }

    private static GpuDto mapGpu(Gpu entity) {
        GpuDto dto = new GpuDto();
        dto.setChipset(entity.getChipset());
        dto.setVramGb(entity.getVramGb());
        dto.setLengthMm(entity.getLengthMm());
        dto.setTdpWatts(entity.getTdpWatts());
        dto.setRecommendedPsuWattage(entity.getRecommendedPsuWattage());
        return dto;
    }

    private static PcCaseDto mapPcCase(PcCase entity) {
        PcCaseDto dto = new PcCaseDto();
        dto.setMaxGpuLengthMm(entity.getMaxGpuLengthMm());
        dto.setMaxCpuCoolerHeightMm(entity.getMaxCpuCoolerHeightMm());
        dto.setMaxRadiatorSizeMm(entity.getMaxRadiatorSizeMm());
        dto.setSupportsAtx(entity.getSupportsAtx());
        dto.setSupportsMicroAtx(entity.getSupportsMicroAtx());
        dto.setSupportsMiniItx(entity.getSupportsMiniItx());
        dto.setMax120mmFans(entity.getMax120mmFans());
        dto.setMax140mmFans(entity.getMax140mmFans());
        return dto;
    }

    private static PowerSupplyDto mapPowerSupply(PowerSupply entity) {
        PowerSupplyDto dto = new PowerSupplyDto();
        dto.setWattage(entity.getWattage());
        dto.setEfficiencyRating(entity.getEfficiencyRating());
        dto.setFormFactor(entity.getFormFactor());
        dto.setIsModular(entity.getIsModular());
        return dto;
    }

    private static StorageDto mapStorage(Storage entity) {
        StorageDto dto = new StorageDto();
        dto.setStorageType(entity.getStorageType());
        dto.setFormFactor(entity.getFormFactor());
        dto.setInterfaceType(entity.getInterfaceType());
        dto.setCapacityGb(entity.getCapacityGb());
        dto.setReadSpeedMbps(entity.getReadSpeedMbps());
        dto.setWriteSpeedMbps(entity.getWriteSpeedMbps());
        return dto;
    }

    private static CpuCoolerDto mapCpuCooler(CpuCooler entity) {
        CpuCoolerDto dto = new CpuCoolerDto();
        dto.setCoolerType(entity.getCoolerType());
        dto.setHeightMm(entity.getHeightMm());
        dto.setRadiatorSizeMm(entity.getRadiatorSizeMm());
        dto.setSupportedSockets(entity.getSupportedSockets());
        dto.setFanSizeMm(entity.getFanSizeMm());
        dto.setIsRgb(entity.getIsRgb());
        return dto;
    }

    private static CaseFanDto mapCaseFan(CaseFan entity) {
        CaseFanDto dto = new CaseFanDto();
        dto.setFanSizeMm(entity.getFanSizeMm());
        dto.setAirflowCfm(entity.getAirflowCfm());
        dto.setNoiseDb(entity.getNoiseDb());
        dto.setIsRgb(entity.getIsRgb());
        return dto;
    }
}
