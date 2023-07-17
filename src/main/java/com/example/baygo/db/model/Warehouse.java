package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "warehouses")
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_gen")
    @SequenceGenerator(name = "warehouse_gen", sequenceName = "warehouse_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String name;
    private String location;
    private String transitWarehouse;
}