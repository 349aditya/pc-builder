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
@Table(name = "gpus")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("GPU")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gpu extends Component {

    @Column(nullable = false)
    private String chipset; // e.g., "RTX 4070 Ti Super", "RX 7800 XT"

    @Column(name = "vram_gb", nullable = false)
    private Integer vramGb;

    @Column(name = "length_mm", nullable = false)
    private Integer lengthMm; // Crucial for physical case clearance checks

    @Column(name = "tdp_watts", nullable = false)
    private Integer tdpWatts; // Crucial for our dynamic system power draw calculator

    @Column(name = "recommended_psu_wattage", nullable = false)
    private Integer recommendedPsuWattage; // Used for PSU validation checks
}