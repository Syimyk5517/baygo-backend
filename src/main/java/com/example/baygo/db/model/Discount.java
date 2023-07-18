package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private int percent;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;
}