package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "chats")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_gen")
    @SequenceGenerator(name = "chat_gen", sequenceName = "chat_seq", allocationSize = 1)
    private Long id;
    @OneToMany(mappedBy = "chat", cascade = ALL)
    private List<Message> messages;
    @OneToOne(mappedBy = "chat", cascade = {MERGE, PERSIST, DETACH, REFRESH})
    private Seller seller;
    @ManyToOne(cascade = {MERGE, PERSIST, DETACH, REFRESH})
    private User user;

    public void addMessage(Message message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
    }

}
