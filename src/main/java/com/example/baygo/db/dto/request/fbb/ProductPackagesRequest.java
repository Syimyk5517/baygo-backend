package com.example.baygo.db.dto.request.fbb;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductPackagesRequest(
        @Positive(message = "Баркод продукта должен быть положительным")
        int packageNumber,
        @Valid
        List<NumberOfProductsRequest> numberOfProductsRequests


) {
}
