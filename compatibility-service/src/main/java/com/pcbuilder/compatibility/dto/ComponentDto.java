package com.pcbuilder.compatibility.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "categoryType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CpuDto.class, name = "CPU"),
        @JsonSubTypes.Type(value = MotherboardDto.class, name = "MOTHERBOARD"),
        @JsonSubTypes.Type(value = RamDto.class, name = "RAM"),
        @JsonSubTypes.Type(value = GpuDto.class, name = "GPU"),
        @JsonSubTypes.Type(value = PcCaseDto.class, name = "CASE"),
        @JsonSubTypes.Type(value = PowerSupplyDto.class, name = "PSU"),
        @JsonSubTypes.Type(value = StorageDto.class, name = "STORAGE"),
        @JsonSubTypes.Type(value = CpuCoolerDto.class, name = "COOLER"),
        @JsonSubTypes.Type(value = CaseFanDto.class, name = "CASE_FAN")
})
public abstract class ComponentDto {
    private Long id;
    private String name;
    private String brand;
    private String model;
}
