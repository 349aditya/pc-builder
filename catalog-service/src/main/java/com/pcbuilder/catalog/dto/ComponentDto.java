package com.pcbuilder.catalog.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
