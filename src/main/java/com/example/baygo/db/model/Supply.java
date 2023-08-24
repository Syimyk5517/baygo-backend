package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.SupplyType;
import com.example.baygo.db.model.enums.SupplyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "supplies")
@NoArgsConstructor
@AllArgsConstructor
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supply_gen")
    @SequenceGenerator(name = "supply_gen", sequenceName = "supply_seq", allocationSize = 1, initialValue = 11)
    private Long id;
    private String supplyNumber;
    @Enumerated(EnumType.STRING)
    private SupplyType supplyType;
    private LocalDate createdAt;
    private LocalDate changedAt;
    private int quantityOfProducts;
    private int acceptedProducts;
    private String commission;
    private BigDecimal supplyCost;
    private LocalDate plannedDate;
    private LocalDate actualDate;
    @Enumerated(EnumType.STRING)
    private SupplyStatus status;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "supply", cascade = ALL)
    private List<SupplyProduct> supplyProduct;

    @OneToOne(cascade = ALL)
    private Supplier supplier;

    public void addSupplyProduct(SupplyProduct supplyProduct) {
        if (this.supplyProduct == null) {
            this.supplyProduct = new ArrayList<>();
        }
        this.supplyProduct.add(supplyProduct);
    }

}