package com.example.baygo.db.dto.response.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class ProductGetByIdResponse {
    private Long productId;
    private Long suProductId;
    private String name;
    private String color;
    private String colorHexCode;
    private int articul;
    private String brand;
    private BigDecimal price;
    private int percentOfDiscount;
    private double rating;
    private Long amountOfReviews;
    private String description;
    private boolean isFavorite;
    private List<String> images;
    private List<ColorsOfSubProductResponse> colors;
    private List<SizeResponse> sizes;

    public ProductGetByIdResponse(Long productId, Long suProductId, String name, String color, String colorHexCode, int articul, String brand, BigDecimal price, int percentOfDiscount, double rating, Long amountOfReviews, String description, boolean isFavorite) {
        this.productId = productId;
        this.suProductId = suProductId;
        this.name = name;
        this.color = color;
        this.colorHexCode = colorHexCode;
        this.articul = articul;
        this.brand = brand;
        this.price = price;
        this.percentOfDiscount = percentOfDiscount;
        this.rating = rating;
        this.amountOfReviews = amountOfReviews;
        this.description = description;
        this.isFavorite = isFavorite;
    }
}


