package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ProductsInBasketResponse {
    private Long sizeId;
    private String productTitle;
    private String productPhoto;
    private Discount discount;
    private int quality;
    private int duty;
    private int cost;
}
