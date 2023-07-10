package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "banners")
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banner_seq")
    @SequenceGenerator(name = "banner_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String image;
}