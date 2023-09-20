package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.AdvertisementPlace;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.PERSIST;

@Getter
@Setter
@Entity
@Table(name = "advertisements")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advertising_gen")
    @SequenceGenerator(name = "advertising_gen", sequenceName = "advertising_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String brand;
    private LocalDate startDate;
    private LocalDate finishDate;
    private BigDecimal companyBudget;
    private double costPerMillennium;
    private int displayForecast;
    private Boolean isDay;
    @Enumerated(EnumType.STRING)
    private AdvertisementPlace advertisementPlace;
    @ElementCollection
    private List<Long> subProductId;
    private Boolean isNew;
    @OneToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Banner banner;
    @OneToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Category category;
    @OneToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Seller seller;

}
