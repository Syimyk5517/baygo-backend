package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mailing_list_subscribers")
@NoArgsConstructor
@AllArgsConstructor
public class MailingListSubscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailing_list_subscriber_gen")
    @SequenceGenerator(name = "mailing_list_subscriber_gen", sequenceName = "mailing_list_subscriber_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String email;
    private boolean isSale;
    private boolean isDiscount;
    private boolean isNew;
}