package com.example.baygo.db.dto.response;

import java.time.LocalDate;

public record ProductResponseForSeller (
     Long productId,
     String mainImage,
     String vendorNumber,
     String productArticle,
     String product,
     String brandName,
     double rating,
     LocalDate dateOfChange,
     String color,
     String size,
     int barcode,
     int quantity
){ }
