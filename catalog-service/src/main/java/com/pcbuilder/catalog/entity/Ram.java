package com.pcbuilder.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ram")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("RAM")
@Getter
@Setter
@NoArgsConstructor
public class Ram extends Component {

    @Column(name = "ram_type", nullable = false)
    private String ramType; // "DDR4" or "DDR5"

    @Column(name = "capacity_gb", nullable = false)
    private Integer capacityGb; // Total capacity of the kit (e.g., 16, 32, 64)

    @Column(name = "stick_count", nullable = false)
    private Integer stickCount; // Number of physical sticks in this kit (e.g., 1, 2, 4)

    @Column(name = "speed_mhz", nullable = false)
    private Integer speedMhz; // Clock speed (e.g., 3200, 6000)

    @Column(name = "cas_latency", nullable = false)
    private Integer casLatency; // Column Access Strobe latency (e.g., 16, 30)
}
