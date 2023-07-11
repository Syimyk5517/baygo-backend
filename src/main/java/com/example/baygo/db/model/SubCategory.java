package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sub_categories")
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_category_gen")
    @SequenceGenerator(name = "sub_category_gen", sequenceName = "sub_category_seq", allocationSize = 1)
    private Long id;
    private String name;
}