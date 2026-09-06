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
@Table(name = "storage")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("STORAGE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
