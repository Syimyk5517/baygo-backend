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
@Table(name = "chats")
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime time;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "chats_buyers",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "buyers_id"))
    private List<Buyer> buyers;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;
    @OneToOne(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Seller seller;
}