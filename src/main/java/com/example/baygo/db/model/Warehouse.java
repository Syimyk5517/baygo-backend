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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_seq")
    @SequenceGenerator(name = "warehouse_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String location;
}