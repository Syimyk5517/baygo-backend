package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.ReturnStatus;
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
@Table(name = "returns")
@NoArgsConstructor
@AllArgsConstructor
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_size_gen")
    @SequenceGenerator(name = "order_size_gen", sequenceName = "order_size_seq", allocationSize = 1, initialValue = 16)
    private Long id;
    private String reason;
    @ElementCollection
    @CollectionTable(name = "return_images", joinColumns = @JoinColumn(name = "return_id"))
    @Column(length = 10000)
    private List<String> images;
    private String country;
    private String city;
    private String address;
    private int postalCode;
    private String phoneNumber;
    private LocalDateTime dateOfReturn;
    private int productQuantity;
    private Boolean withDelivery;
    @Enumerated(EnumType.STRING)
    private ReturnStatus returnStatus;
    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private OrderSize orderSize;
}
