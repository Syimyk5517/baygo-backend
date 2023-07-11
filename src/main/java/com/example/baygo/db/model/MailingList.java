package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "mailing_lists")
@NoArgsConstructor
@AllArgsConstructor
public class MailingList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailing_list_gen")
    @SequenceGenerator(name = "mailing_list_gen", sequenceName = "mailing_list_seq", allocationSize = 1)
    private Long id;
    private String image;
    private String name;
    private String description;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;
}