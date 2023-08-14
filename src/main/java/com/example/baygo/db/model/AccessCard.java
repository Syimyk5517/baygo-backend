package com.example.baygo.db.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "access_cards")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_card_gen")
    @SequenceGenerator(name = "access_card_gen", sequenceName = "access_card_seq", allocationSize = 1, initialValue = 3)
    private Long id;
    private String driverFirstName;
    private String driverLastName;
    private String carBrand;
    private int numberOfCar;
    @OneToOne(mappedBy = "accessCard")
    private Supply supply;

}
