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
    @SequenceGenerator(name = "buyer_gen", sequenceName = "bayer_seq", allocationSize = 1, initialValue = 3)
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String photo;

    @OneToMany(mappedBy = "buyer", cascade = ALL)
    private List<Appeal> appeals;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "buyers_favorites",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> favorites;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "buyers_baskets",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_size_id"))
    private List<Size> basket;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "buyers_last_views",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_products_id"))
    private List<SubProduct> lastViews;

    @OneToMany(mappedBy = "buyer",cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Order> orders;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public void addBasket(Size size) {
        basket.add(size);
    }
}