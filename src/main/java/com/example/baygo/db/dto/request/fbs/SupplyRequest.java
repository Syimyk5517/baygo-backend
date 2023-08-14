package com.example.baygo.db.dto.request.fbs;

import lombok.Builder;

import java.util.List;

@Builder
public record SupplyRequest(
        Long wareHouseId,
      List<SupplySizeQuantityRequest> supplySizeQuantityRequestList
) {
}
