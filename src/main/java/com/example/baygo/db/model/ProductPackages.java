package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import java.util.Map;


@Getter
@Setter
@Entity
@Table(name = "product_packages")
@NoArgsConstructor
@AllArgsConstructor
public class ProductPackages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_packages_gen")
    @SequenceGenerator(name = "product_packages_gen", sequenceName = "product_packages_seq", allocationSize = 1, initialValue = 16)
    private Long id;
    private int packageNumber;
    @ElementCollection
    @JoinTable(name = "supply_products_product_packages",
            joinColumns = @JoinColumn(name = "product_package_id"))
    @MapKeyJoinColumn(name = "supply_product_id")
    @Cascade({CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private Map<SupplyProduct, Integer> productCounts;

}
