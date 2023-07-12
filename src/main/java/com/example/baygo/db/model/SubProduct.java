package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "sub_products")
@NoArgsConstructor
@AllArgsConstructor
public class SubProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_product_gen")
    @SequenceGenerator(name = "sub_product_gen", sequenceName = "sub_product_seq", allocationSize = 1)
    private Long id;
    private String colorHexCode;
    private String color;
    @ElementCollection
    private List<String> images;
    private BigDecimal price;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany(cascade = ALL)
    @JoinTable(name = "sub_product_size")
    List<Size> sizes;

}