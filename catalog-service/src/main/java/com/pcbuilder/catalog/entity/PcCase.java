package com.pcbuilder.catalog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Table(name = "pc_cases")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("CASE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PcCase extends Component {

    @Column(name = "max_gpu_length_mm", nullable = false)
    private Integer maxGpuLengthMm;

    @Column(name = "max_cpu_cooler_height_mm", nullable = false)
    private Integer maxCpuCoolerHeightMm;

    @Column(name = "max_radiator_size_mm", nullable = false)
    private Integer maxRadiatorSizeMm; // e.g., 240, 280, 360 (0 if none supported)

    @Column(name = "supports_atx", nullable = false)
    private Boolean supportsAtx = false;

    @Column(name = "supports_micro_atx", nullable = false)
    private Boolean supportsMicroAtx = false;

    @Column(name = "supports_mini_itx", nullable = false)
    private Boolean supportsMiniItx = false;

    @Column(name = "max_120mm_fans", nullable = false)
    private Integer max120mmFans = 0;

    @Column(name = "max_140mm_fans", nullable = false)
    private Integer max140mmFans = 0;
}