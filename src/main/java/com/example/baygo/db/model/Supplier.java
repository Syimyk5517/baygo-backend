package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.SupplyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "supplier")
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_gen")
    @SequenceGenerator(name = "supplier_gen", sequenceName = "supplier_seq", allocationSize = 1, initialValue = 11)
    private Long id;
    private String nameOfSupplier;
    private String surnameOfSupplier;
    private String carBrand;
    private String carNumber;
    private SupplyType supplyType;
}
