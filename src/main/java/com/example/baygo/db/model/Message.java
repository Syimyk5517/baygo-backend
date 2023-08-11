package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_gen")
    @SequenceGenerator(name = "message_gen", sequenceName = "message_seq", allocationSize = 1)
    private Long id;
    private String message;
    private String image;
    private LocalDateTime time;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Chat chat;
    private Boolean isSeller;
}
