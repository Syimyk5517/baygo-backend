package com.example.baygo.db.dto.request.fbs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SupplyRequest(
        @NotNull(message = "Идентификатор склада не может быть пустым")
        Long wareHouseId,
        @Valid
        List<SupplySizeQuantityRequest> supplySizeQuantityRequestList
) {
}
