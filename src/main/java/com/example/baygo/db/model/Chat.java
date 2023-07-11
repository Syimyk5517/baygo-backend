package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "chats")
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_gen")
    @SequenceGenerator(name = "chat_gen", sequenceName = "chat_seq", allocationSize = 1)
    private Long id;
    private LocalDateTime time;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinTable(name = "chats_buyers",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "buyers_id"))
    private List<Buyer> buyers;

    @OneToMany(mappedBy = "chat", cascade = ALL)
    private List<Message> messages;

    @OneToOne(cascade = CascadeType.ALL)
    private Seller seller;
}