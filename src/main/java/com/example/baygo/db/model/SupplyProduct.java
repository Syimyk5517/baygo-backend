package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "supply_products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supply_product_gen")
    @SequenceGenerator(name = "supply_product_gen", sequenceName = "supply_product_seq", allocationSize = 1,initialValue = 80)
    private Long id;
    private int quantity;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "supply_id")
    private Supply supply;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "size_id")
    private Size size;

}
