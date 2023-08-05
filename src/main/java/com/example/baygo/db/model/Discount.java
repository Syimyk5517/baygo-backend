package com.example.baygo.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Entity
@Table(name = "discounts")
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_gen")
    @SequenceGenerator(name = "discount_gen", sequenceName = "discount_seq", allocationSize = 1, initialValue = 11)
    private Long id;
    private String nameOfDiscount;
    private int percent;
    private LocalDateTime dateOfStart;
    private LocalDateTime dateOfFinish;
    @OneToMany(mappedBy = "discount", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<SubProduct> subProducts;
}