package com.example.baygo.db.dto.response.seller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SellerReturnGetByIdResponse {
    private Long id;
    private String barcode;
    private String mainImage;
    private String productName;
    private String size;
    private int quantity;
    private String reason;
    private List<String> images;

    public SellerReturnGetByIdResponse(Long id, String barcode, String mainImage, String productName, String size, int quantity, String reason) {
        this.id = id;
        this.barcode = barcode;
        this.mainImage = mainImage;
        this.productName = productName;
        this.size = size;
        this.quantity = quantity;
        this.reason = reason;
    }
}
