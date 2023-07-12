package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "buyers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyer_gen")
    @SequenceGenerator(name = "buyer_gen", sequenceName = "bayer_seq", allocationSize = 1)
    private Long id;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;

    @OneToMany(mappedBy = "buyer", cascade = ALL)
    private List<Appeal> appeals;

    @ManyToMany(mappedBy = "buyers", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Chat> chats;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> favorites;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> basket;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> lastViews;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinTable(name = "buyers_sub_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private Map<Integer, SubProduct> prodCount;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    private User user;
}