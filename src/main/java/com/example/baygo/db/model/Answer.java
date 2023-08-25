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
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_gen")
    @SequenceGenerator(name = "answer_gen", sequenceName = "answer_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String suggestionOrComment;
    private int rating;
    @ManyToMany(mappedBy = "answer", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Option>options;
}
