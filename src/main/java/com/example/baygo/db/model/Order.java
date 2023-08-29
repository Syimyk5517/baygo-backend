package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen")
    @SequenceGenerator(name = "order_gen", sequenceName = "order_seq", allocationSize = 1, initialValue = 16)
    private Long id;
    private LocalDateTime dateOfOrder;
    private BigDecimal totalPrice;
    private BigDecimal discountPrice;
    private BigDecimal resultPrice;
    private boolean withDelivery;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private String orderNumber;

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @OneToMany(mappedBy = "order", cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private List<OrderSize> orderSizes;

    @OneToOne(cascade = ALL)
    private Customer customer;
}