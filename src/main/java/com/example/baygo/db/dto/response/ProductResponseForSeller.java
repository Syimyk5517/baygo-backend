package com.example.baygo.db.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ProductResponseForSeller(
        Long productId,
        Long subProductId,
        String image,
        String articulOfSeller,
        String articulBG,
        String product,
        String brand,
        double rating,
        LocalDate dateOfChange,
        String color,
        String size,
        String barcode,
        int quantity,
        List<SizeSellerResponse> sizes
) {
}
