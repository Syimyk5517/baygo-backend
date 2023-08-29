package com.example.baygo.db.dto.response.fbs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllFbsOrderBySupplyId {
    private String image;
    private int barcode;
    private int quantity;
    private String productName;
    private String size;
    private String color;
    private BigDecimal price;
    private LocalDateTime dateTime;
}
