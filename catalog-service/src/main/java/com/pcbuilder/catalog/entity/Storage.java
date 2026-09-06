package com.pcbuilder.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "storage")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("STORAGE")
@Getter
@Setter
@NoArgsConstructor
public class Storage extends Component {

    @Column(name = "storage_type", nullable = false)
    private String storageType; // "SSD" or "HDD"

    @Column(name = "form_factor", nullable = false)
    private String formFactor; // "M.2", "2.5\"", "3.5\""

    @Column(name = "interface_type", nullable = false)
    private String interfaceType; // "SATA III", "PCIe NVMe Gen 4", etc.

    @Column(name = "capacity_gb", nullable = false)
    private Integer capacityGb;

    @Column(name = "read_speed_mbps")
    private Integer readSpeedMbps;

    @Column(name = "write_speed_mbps")
    private Integer writeSpeedMbps;
}
