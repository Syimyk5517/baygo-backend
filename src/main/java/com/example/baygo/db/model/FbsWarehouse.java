package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fbs_warehouses")
@NoArgsConstructor
@AllArgsConstructor
public class FbsWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fbs_warehouse_gen")
    @SequenceGenerator(name = "fbs_warehouse_gen", sequenceName = "fbs_warehouse_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String name;
    private String country;
    private String city;
    private String street;
    private int indexOfCountry;
    private int houseNumber;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<DayOfWeek> workingDay;
    private String preparingSupply;
    private String assemblyTime;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

}
