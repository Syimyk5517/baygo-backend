package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sizes")
@NoArgsConstructor
@AllArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sizes_gen")
    @SequenceGenerator(name = "sizes_gen", sequenceName = "sizes_seq", allocationSize = 1)
    private Long id;
    private String size;
    private int barcode;
    int quantity;
}
