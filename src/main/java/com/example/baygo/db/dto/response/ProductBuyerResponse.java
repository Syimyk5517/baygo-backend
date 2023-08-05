package com.example.baygo.db.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductBuyerResponse {
    private Long sizeId;
    private Long subProductId;
    private Long productId;
    private String image;
    private String name;
    private String description;
    private double rating;
    private Long quantityOfRating;
    private BigDecimal price;
    private int percentOfDiscount;

    public ProductBuyerResponse(Long sizeId, Long subProductId, Long productId, String name, String description, double rating, Long quantityOfRating, BigDecimal price, int percentOfDiscount) {
        this.sizeId = sizeId;
        this.subProductId = subProductId;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.quantityOfRating = quantityOfRating;
        this.price = price;
        this.percentOfDiscount = percentOfDiscount;
    }
}
