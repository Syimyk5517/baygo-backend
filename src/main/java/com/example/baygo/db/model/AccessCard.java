package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.SupplyType;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

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
    private String barcode;
    private String barcodeImage;
    private String driverFirstName;
    private String driverLastName;
    private String carBrand;
    private String numberOfCar;
    private int numberOfSeats;
    @Enumerated(EnumType.STRING)
    private SupplyType supplyType;
    @OneToOne(cascade = {DETACH,REFRESH,PERSIST,MERGE})
    private Supply supply;

}
