package com.example.baygo.db.model;

import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fbs_warehouses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @ElementCollection
    @CollectionTable(name = "fbs_warehouse_working_day", joinColumns = @JoinColumn(name = "fbs_warehouse_id"))
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> workingDay;
    private int preparingSupply;
    private int assemblyTime;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
    @ManyToMany
    private List<Size> sizes;

    public void addProductQuantity(Long sizeId, int quantity) {
        for (Size size : sizes) {
            if (size.getId().equals(sizeId)) {
                size.setFbsQuantity(size.getFbsQuantity() + quantity);
                return;
            }
        }

        throw new NotFoundException("Размер с заданным идентификатором не найден");
    }
}

