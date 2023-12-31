package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@Table(name = "sellers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_gen")
    @SequenceGenerator(name = "seller_gen", sequenceName = "seller_seq", allocationSize = 1, initialValue = 4)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String nameOfStore;
    private String storeLogo;
    private String ITN;
    private String BIC;
    private String aboutStore;
    private String vendorNumber;
    private String addressOfStore;
    private int checkingCheck;

    @OneToMany(mappedBy = "seller", cascade = ALL)
    private List<Supply> supplies;

    @OneToMany(mappedBy = "seller", cascade = ALL)
    private List<Product> products;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = ALL)
    private Chat chat;

    @OneToMany(mappedBy = "seller", cascade = ALL)
    private List<FBSSupply> fbsSupplies;


   @OneToMany(mappedBy = "seller", cascade = ALL)
    private List<FbsWarehouse> fbsWarehouse;

   @ManyToOne
   @JoinColumn(name = "warehouse_of_returns_id")
   private Warehouse warehouseOfReturns;
}