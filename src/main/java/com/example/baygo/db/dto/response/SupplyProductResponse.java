package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class SupplyProductResponse {
    Long productId;
    Long subProductId;
    Long sizeId;
    String photo;
    int barcode;
    int quantity;
    String productName;
    String sellerVendorCode;
    String brandName;
    String size;
    String color;
}