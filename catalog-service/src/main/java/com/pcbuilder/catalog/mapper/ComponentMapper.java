package com.pcbuilder.catalog.mapper;

import com.pcbuilder.catalog.dto.*;
import com.pcbuilder.catalog.entity.*;

public class ComponentMapper {

    public static Component toEntity(ComponentDto dto) {
        if (dto == null) throw new IllegalArgumentException("Component body is required.");
        Component entity = switch (dto) {
            case CpuDto ignored -> new Cpu();
            case MotherboardDto ignored -> new Motherboard();
            case RamDto ignored -> new Ram();
            case GpuDto ignored -> new Gpu();
            case PcCaseDto ignored -> new PcCase();
            case PowerSupplyDto ignored -> new PowerSupply();
            case StorageDto ignored -> new Storage();
            case CpuCoolerDto ignored -> new CpuCooler();
            case CaseFanDto ignored -> new CaseFan();
            default -> throw new IllegalArgumentException("Unsupported component category.");
        };
        updateEntity(entity, dto);
        return entity;
    }

    public static void updateEntity(Component entity, ComponentDto dto) {
        if (!matchesType(entity, dto)) {
            throw new IllegalArgumentException("A component's category cannot be changed.");
        }
        entity.setName(dto.getName());
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setPrice(dto.getPrice());
        entity.setStockQuantity(dto.getStockQuantity());
        entity.setImageUrl(dto.getImageUrl());

        switch (entity) {
            case Cpu value -> {
                CpuDto source = (CpuDto) dto;
                value.setSocketType(source.getSocketType()); value.setCoreCount(source.getCoreCount());
                value.setThreadCount(source.getThreadCount()); value.setBaseClockGhz(source.getBaseClockGhz());
                value.setBoostClockGhz(source.getBoostClockGhz()); value.setTdpWatts(source.getTdpWatts());
                value.setHasIntegratedGraphics(source.getHasIntegratedGraphics());
            }
            case Motherboard value -> {
                MotherboardDto source = (MotherboardDto) dto;
                value.setSocketType(source.getSocketType()); value.setFormFactor(source.getFormFactor());
                value.setChipset(source.getChipset()); value.setRamType(source.getRamType());
                value.setRamSlots(source.getRamSlots()); value.setMaxRamCapacityGb(source.getMaxRamCapacityGb());
                value.setM2Slots(source.getM2Slots()); value.setSataPorts(source.getSataPorts());
            }
            case Ram value -> {
                RamDto source = (RamDto) dto;
                value.setRamType(source.getRamType()); value.setCapacityGb(source.getCapacityGb());
                value.setStickCount(source.getStickCount()); value.setSpeedMhz(source.getSpeedMhz());
                value.setCasLatency(source.getCasLatency());
            }
            case Gpu value -> {
                GpuDto source = (GpuDto) dto;
                value.setChipset(source.getChipset()); value.setVramGb(source.getVramGb());
                value.setLengthMm(source.getLengthMm()); value.setTdpWatts(source.getTdpWatts());
                value.setRecommendedPsuWattage(source.getRecommendedPsuWattage());
            }
            case PcCase value -> {
                PcCaseDto source = (PcCaseDto) dto;
                value.setMaxGpuLengthMm(source.getMaxGpuLengthMm());
                value.setMaxCpuCoolerHeightMm(source.getMaxCpuCoolerHeightMm());
                value.setMaxRadiatorSizeMm(source.getMaxRadiatorSizeMm()); value.setSupportsAtx(source.getSupportsAtx());
                value.setSupportsMicroAtx(source.getSupportsMicroAtx()); value.setSupportsMiniItx(source.getSupportsMiniItx());
                value.setMax120mmFans(source.getMax120mmFans()); value.setMax140mmFans(source.getMax140mmFans());
            }
            case PowerSupply value -> {
                PowerSupplyDto source = (PowerSupplyDto) dto;
                value.setWattage(source.getWattage()); value.setEfficiencyRating(source.getEfficiencyRating());
                value.setFormFactor(source.getFormFactor()); value.setIsModular(source.getIsModular());
            }
            case Storage value -> {
                StorageDto source = (StorageDto) dto;
                value.setStorageType(source.getStorageType()); value.setFormFactor(source.getFormFactor());
                value.setInterfaceType(source.getInterfaceType()); value.setCapacityGb(source.getCapacityGb());
                value.setReadSpeedMbps(source.getReadSpeedMbps()); value.setWriteSpeedMbps(source.getWriteSpeedMbps());
            }
            case CpuCooler value -> {
                CpuCoolerDto source = (CpuCoolerDto) dto;
                value.setCoolerType(source.getCoolerType()); value.setHeightMm(source.getHeightMm());
                value.setRadiatorSizeMm(source.getRadiatorSizeMm()); value.setSupportedSockets(source.getSupportedSockets());
                value.setFanSizeMm(source.getFanSizeMm()); value.setIsRgb(source.getIsRgb());
            }
            case CaseFan value -> {
                CaseFanDto source = (CaseFanDto) dto;
                value.setFanSizeMm(source.getFanSizeMm()); value.setAirflowCfm(source.getAirflowCfm());
                value.setNoiseDb(source.getNoiseDb()); value.setIsRgb(source.getIsRgb());
            }
            default -> throw new IllegalArgumentException("Unsupported component category.");
        }
    }

    private static boolean matchesType(Component entity, ComponentDto dto) {
        return (entity instanceof Cpu && dto instanceof CpuDto)
                || (entity instanceof Motherboard && dto instanceof MotherboardDto)
                || (entity instanceof Ram && dto instanceof RamDto)
                || (entity instanceof Gpu && dto instanceof GpuDto)
                || (entity instanceof PcCase && dto instanceof PcCaseDto)
                || (entity instanceof PowerSupply && dto instanceof PowerSupplyDto)
                || (entity instanceof Storage && dto instanceof StorageDto)
                || (entity instanceof CpuCooler && dto instanceof CpuCoolerDto)
                || (entity instanceof CaseFan && dto instanceof CaseFanDto);
    }

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
