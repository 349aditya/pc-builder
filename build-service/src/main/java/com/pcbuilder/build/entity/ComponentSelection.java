package com.pcbuilder.build.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentSelection {
    @Column(name = "component_id", nullable = false)
    private Long componentId;

    @Column(nullable = false)
    private Integer quantity;
}
