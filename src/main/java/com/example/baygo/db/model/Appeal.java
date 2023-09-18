package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "appeals")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appeal_gen")
    @SequenceGenerator(name = "appeal_gen", sequenceName = "appeal_seq", allocationSize = 1, initialValue = 3)
    private Long id;
    private String title;
    private String detailedAppeal;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
}