package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.FBSSupplyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fbs_supplies")
@NoArgsConstructor
@AllArgsConstructor
public class FBSSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fbs_supply_gen")
    @SequenceGenerator(name = "fbs_supply_gen", sequenceName = "fbs_supply_seq", allocationSize = 1, initialValue = 16)
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;
    private int quantityOfProducts;
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private FBSSupplyStatus fbsSupplyStatus;

    @OneToMany(mappedBy = "fbsSupply", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private List<OrderSize> orderSizes;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
