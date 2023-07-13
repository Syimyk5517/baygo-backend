package com.example.baygo.db.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductResponseForSeller {
    private Long productId;
    private String mainImage;
    private String vendorNumber;
    private String productArticle;
    private String product;
    private String brandName;
    private double rating;
    private LocalDate dateOfChange;
    private String color;
    private String size;
    private int barcode;
    private int quantity;
}
