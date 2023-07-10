package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    private String postalCode;
    private String address;
}