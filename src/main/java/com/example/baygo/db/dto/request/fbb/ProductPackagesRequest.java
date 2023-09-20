package com.example.baygo.db.dto.request.fbb;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductPackagesRequest(
        @NotBlank(message = "Необходимо указать баркод коробку.")
        String packageBarcode,
        @NotBlank(message = "Необходимо указать фото баркод коробку.")
        String packageBarcodeImage,
        @Valid
        List<NumberOfProductsRequest> numberOfProductsRequests


) {
}
