package com.example.baygo.db.model;

import com.example.baygo.db.enums.PaymentType;
import com.example.baygo.db.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate dateOfOrder;
    private LocalDate dateOfReceived;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean withDelivery;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private String orderNumber;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST}, orphanRemoval = true)
    private List<Buyer> buyers ;
}