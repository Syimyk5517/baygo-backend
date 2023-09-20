package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "warehouses")
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_gen")
    @SequenceGenerator(name = "warehouse_gen", sequenceName = "warehouse_seq", allocationSize = 1, initialValue = 16)
    private Long id;
    private String name;
    private String region;
    private String address;
    private BigDecimal transitCost;
    @OneToMany(mappedBy = "warehouse", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private List<FBSSupply> fbsSupply;


}