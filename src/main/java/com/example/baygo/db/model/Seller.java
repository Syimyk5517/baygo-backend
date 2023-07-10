package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sellers")
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_seq")
    @SequenceGenerator(name = "seller_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String photo;
    private String address;
    private String nameOfStore;
    private String storeLogo;
    private String ITN;
    private String BIC;
    private String aboutStore;
    private String vendorCodeOfSeller;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Supply> supplies;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "seller_id")
    private List<Product> products;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;
}