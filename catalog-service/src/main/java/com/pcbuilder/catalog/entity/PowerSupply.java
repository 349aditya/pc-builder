package com.pcbuilder.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "psus")
@PrimaryKeyJoinColumn(name = "component_id")
@DiscriminatorValue("PSU")
@Getter
@Setter
@NoArgsConstructor
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