package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", allocationSize = 1, initialValue = 10)
    @Column(name = "id", nullable = false)
    private Long id;
    private String tittle;

    @Column(length = 2000)
    private String message;
    private LocalDateTime createAt;
    private Boolean read;
    @ManyToMany(cascade = {MERGE, REFRESH, DETACH, PERSIST})
    @JoinTable(name = "notifications_buyers",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "buyer_id"))
    private List<Buyer> buyers;

    public void  addBuyer(Buyer buyer){
        if (this.buyers == null){
            this.buyers = new ArrayList<>();
        }
        this.buyers.add(buyer);
    }

}