package com.pcbuilder.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "case_fans")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("CASE_FAN")
@Getter
@Setter
@NoArgsConstructor
public class CaseFan extends Component {

    @Column(name = "fan_size_mm", nullable = false)
    private Integer fanSizeMm; // e.g., 120, 140

    @Column(name = "airflow_cfm", nullable = false)
    private Double airflowCfm; //max airflow

    @Column(name = "noise_db")
    private Double noiseDb; // max noise rating for the fan l

    @Column(name = "is_rgb", nullable = false)
    private Boolean isRgb = false;
}
