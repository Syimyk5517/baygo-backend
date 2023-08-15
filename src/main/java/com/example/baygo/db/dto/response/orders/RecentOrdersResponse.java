package com.example.baygo.db.dto.response.orders;

import com.example.baygo.db.model.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecentOrdersResponse {
    private Long orderId;
    private int articulBG;
    private String productName;
    private BigDecimal price;
    private int productCount;
    private Status status;

    public RecentOrdersResponse(Long orderId, int articulBG, String productName, BigDecimal price, int productCount, Status status) {
        this.orderId = orderId;
        this.articulBG = articulBG;
        this.productName = productName;
        this.price = price;
        this.productCount = productCount;
        this.status = status;
    }
}

