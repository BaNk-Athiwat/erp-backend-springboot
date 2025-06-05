package com.app.erp_backend_springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FormularEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer qty;
    private String unitName;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private MaterialEntity material;

    @ManyToOne
    @JoinColumn(name = "production_id")
    private ProductionEntity production;
}
