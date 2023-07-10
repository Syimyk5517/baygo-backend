package com.example.baygo.db.model;

import com.example.baygo.db.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "buyers")
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyer_seq")
    @SequenceGenerator(name = "buyer_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appeal> appeals;
    @ManyToMany(mappedBy = "buyers", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Chat> chats;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> favorites;
    @ManyToMany(cascade =  CascadeType.ALL)
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> basket;
    @ManyToMany(cascade =  CascadeType.ALL)
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> lastViews;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private Map<Integer, SubProduct> prodCount;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;
}