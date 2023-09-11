package com.example.baygo.db.model;


import com.example.baygo.db.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders_sizes")
@NoArgsConstructor
@AllArgsConstructor
public class OrderSize {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_size_gen")
    @SequenceGenerator(name = "order_size_gen", sequenceName = "order_size_seq", allocationSize = 1, initialValue = 16)
    private Long id;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime dateOfReceived;
    private String qrCode;
    private boolean isFbsOrder;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "fbs_supply_id")
    private FBSSupply fbsSupply;
    @OneToOne(mappedBy = "orderSize", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private Return returns;
}
