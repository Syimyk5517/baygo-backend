package com.example.baygo.db.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

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
    @Value(value = "3000000")
    private List<String> images;
    private String country;
    private String city;
    private String address;
    private int postalCode;
    private String phoneNumber;
    private LocalDateTime dateOfReturn;
}
