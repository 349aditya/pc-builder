package com.pcbuilder.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coolers")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("COOLER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpuCooler extends Component {

    @Column(name = "cooler_type", nullable = false)
    private String coolerType; // AIR or liquid

    @Column(name = "height_mm")
    private Integer heightMm; // for air cooler

    @Column(name = "radiator_size_mm")
    private Integer radiatorSizeMm; // Liquid coolers e.g., 240, 280, 360

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "cooler_sockets",
            joinColumns = @JoinColumn(name = "cooler_id")
    )
    @Column(name = "socket_type", nullable = false)
    private Set<String> supportedSockets = new HashSet<>();

    @Column(name = "fan_size_mm")
    private Integer fanSizeMm; // e.g., 120, 140

    @Column(name = "is_rgb", nullable = false)
    private Boolean isRgb = false;
}