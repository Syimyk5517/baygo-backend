package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.SupplyType;
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
    private String numberOfCar;
    @Enumerated(EnumType.STRING)
    private SupplyType supplyType;
    @OneToOne(mappedBy = "accessCard")
    private FBSSupply fbsSupply;
    @OneToOne(mappedBy = "accessCard")
    private Supply supply;

}
