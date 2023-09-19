package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @Column(length = 10000)
    private String url;
    @OneToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Banner banner;
    @OneToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Category category;
    @OneToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private User user;

}
