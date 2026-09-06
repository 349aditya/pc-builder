package com.pcbuilder.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cpus")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("CPU")
@Getter
@Setter
@NoArgsConstructor
public class Cpu extends Component {

    @Column(name = "socket_type", nullable = false)
    private String socketType;

    @Column(name = "core_count", nullable = false)
    private Integer coreCount;

    @Column(name = "thread_count", nullable = false)
    private Integer threadCount;

    @Column(name = "base_clock_ghz", nullable = false, precision = 3, scale = 2)
    private BigDecimal baseClockGhz;

    @Column(name = "boost_clock_ghz", nullable = false, precision = 3, scale = 2)
    private BigDecimal boostClockGhz;

    @Column(name = "tdp_watts", nullable = false)
    private Integer tdpWatts;

    @Column(name = "has_integrated_graphics", nullable = false)
    private Boolean hasIntegratedGraphics = false;
}
