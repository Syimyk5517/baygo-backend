package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "sizes")
@NoArgsConstructor
@AllArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "size_gen")
    @SequenceGenerator(name = "size_gen", sequenceName = "size_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String size;
    private int barcode;
    private int quantity;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "sub_product_id")
    private SubProduct subProduct;
}