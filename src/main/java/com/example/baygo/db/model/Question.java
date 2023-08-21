package com.example.baygo.db.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "questions")
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_gen")
    @SequenceGenerator(name = "question_gen", sequenceName = "question_seq", allocationSize = 1, initialValue = 20)
    private Long id;
    private String title;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Option> options;
    @ManyToOne
    private Survey survey;
}
