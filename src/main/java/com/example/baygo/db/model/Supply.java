package com.example.baygo.db.model;

import com.example.baygo.db.enums.SupplyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "supplies")
@NoArgsConstructor
@AllArgsConstructor
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supply_seq")
    @SequenceGenerator(name = "supply_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String supplyNumber;
    private String supplyType;
    private LocalDate createdAt;
    private int quantityOfProducts;
    private int acceptedProducts;
    private int commission;
    private BigDecimal supplyCost;
    private LocalDate plannedDate;
    private LocalDate actualDate;
    @Enumerated(EnumType.STRING)
    private SupplyStatus status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "seller_id")
    private Seller seller;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "supplies_sub_products",
            joinColumns = @JoinColumn(name = "supply_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> subProducts;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
}