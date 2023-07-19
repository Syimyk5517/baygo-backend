package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "buyer_questions")
@NoArgsConstructor
@AllArgsConstructor
public class BuyerQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyer_question_gen")
    @SequenceGenerator(name = "buyer_question_gen", sequenceName = "buyer_question_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 1000)
    private String question;

    @Column(length = 1000)
    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime replyDate;
}
