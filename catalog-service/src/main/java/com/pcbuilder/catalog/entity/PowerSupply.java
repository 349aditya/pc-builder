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
@Table(name = "psus")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("PSU")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PowerSupply extends Component {

    @Column(nullable = false)
    private Integer wattage; // Dynamic system power budget check

    @Column(name = "efficiency_rating")
    private String efficiencyRating; // e.g., "80+ Gold", "80+ Platinum"

    @Column(name = "form_factor", nullable = false)
    private String formFactor = "ATX"; // e.g., "ATX", "SFX" (for ITX builds)

    @Column(name = "is_modular", nullable = false)
    private Boolean isModular = true;
}