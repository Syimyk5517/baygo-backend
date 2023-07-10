package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String text;
    private int grade;
    private String answer;
    private int amountOfLike;
    @ElementCollection
    private List<String> images;
    private LocalDateTime dateAndTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
}