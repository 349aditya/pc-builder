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
@Table(name = "motherboards")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("MOTHERBOARD")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Motherboard extends Component {

    @Column(name = "socket_type", nullable = false)
    private String socketType;

    @Column(name = "form_factor", nullable = false)
    private String formFactor; // e.g., "ATX", "Micro-ATX", "Mini-ITX"

    @Column(nullable = false)
    private String chipset; // e.g., "B650", "Z790"

    @Column(name = "ram_type", nullable = false)
    private String ramType; // "DDR4" or "DDR5"

    @Column(name = "ram_slots", nullable = false)
    private Integer ramSlots;

    @Column(name = "max_ram_capacity_gb", nullable = false)
    private Integer maxRamCapacityGb;

    @Column(name = "m2_slots", nullable = false)
    private Integer m2Slots;

    @Column(name = "sata_ports", nullable = false)
    private Integer sataPorts;
}
