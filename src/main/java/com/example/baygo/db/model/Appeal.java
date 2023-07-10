package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appeals")
@NoArgsConstructor
@AllArgsConstructor
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appeal_seq")
    @SequenceGenerator(name = "appeal_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String divide;
    private String detailedAppeal;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
}