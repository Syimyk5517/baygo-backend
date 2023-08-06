package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "options")
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_gen")
    @SequenceGenerator(name = "option_gen", sequenceName = "option_seq", allocationSize = 1, initialValue = 20)
    private Long id;
    private String title;
    private int optionOrder;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Question question;
    @ManyToOne(cascade = CascadeType.ALL)
    private Answer answer;
}
