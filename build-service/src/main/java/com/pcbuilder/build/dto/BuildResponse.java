package com.pcbuilder.build.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BuildResponse {
    private Long id;
    private String name;
    private List<BuildItemDto> components = new ArrayList<>();
    private BigDecimal totalPrice;
    private boolean compatible;
    private Integer estimatedWattage;
    private List<CompatibilityMessageDto> compatibilityMessages = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
