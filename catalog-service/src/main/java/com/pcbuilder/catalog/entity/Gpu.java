package com.pcbuilder.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gpus")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("GPU")
@Getter
@Setter
@NoArgsConstructor
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