package com.example.baygo.db.dto.response.fbs;

import com.example.baygo.db.dto.request.fbs.ProductRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record FBSWareHouseAddProduct(
        Long wareHouseId,
        List<ProductRequest> productRequestList
) {
}
