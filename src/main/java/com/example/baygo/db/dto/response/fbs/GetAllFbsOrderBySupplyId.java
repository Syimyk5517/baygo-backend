package com.example.baygo.db.dto.response.fbs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllFbsOrderBySupplyId {
    private String image;
    private String barcode;
    private int quantity;
    private String productName;
    private String size;
    private String color;
    private BigDecimal price;
    private LocalDate dateOfOrder;
}
