package com.example.baygo.db.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyProductResponse {
    private Long productId;
    private Long subProductId;
    private Long sizeId;
    private String image;
    private int barcode;
    private int quantity;
    private String name;
    private String vendorNumber;
    private String brand;
    private String sizes;
    private String color;

    public SupplyProductResponse(Long productId, Long subProductId, Long sizeId, String image, int barcode, int quantity, String name, String vendorNumber, String brand, String sizes, String color) {
        this.productId = productId;
        this.subProductId = subProductId;
        this.sizeId = sizeId;
        this.image = image;
        this.barcode = barcode;
        this.quantity = quantity;
        this.name = name;
        this.vendorNumber = vendorNumber;
        this.brand = brand;
        this.sizes = sizes;
        this.color = color;
    }


}
