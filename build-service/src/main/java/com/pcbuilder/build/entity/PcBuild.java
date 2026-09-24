package com.pcbuilder.build.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pc_builds")
@Getter
@Setter
@NoArgsConstructor
public class PcBuild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    private Long cpuId;
    private Long motherboardId;
    private Long gpuId;
    private Long coolerId;
    private Long psuId;
    private Long pcCaseId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "componentId", column = @Column(name = "ram_component_id")),
            @AttributeOverride(name = "quantity", column = @Column(name = "ram_quantity"))
    })
    private ComponentSelection ramSelection;

    @ElementCollection
    @CollectionTable(name = "build_storage_selections", joinColumns = @JoinColumn(name = "build_id"))
    @OrderColumn(name = "selection_order")
    private List<ComponentSelection> storageSelections = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "build_case_fan_selections", joinColumns = @JoinColumn(name = "build_id"))
    @OrderColumn(name = "selection_order")
    private List<ComponentSelection> caseFanSelections = new ArrayList<>();

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean compatible = true;

    @Column(nullable = false)
    private Integer estimatedWattage = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
