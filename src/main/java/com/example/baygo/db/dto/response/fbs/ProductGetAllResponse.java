package com.example.baygo.db.dto.response.fbs;

import lombok.Builder;

@Builder
public record ProductGetAllResponse(
      Long sizeId,
      String photo,
      String barcode,
      String productName,
      String articulOfSeller,
      String brand,
      String size,
      String color,
      String address

) {
}
