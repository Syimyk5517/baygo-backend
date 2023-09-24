package com.example.baygo.db.dto.request.fbb;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record SupplyWrapperRequest(
        @NotNull(message = "Необходимо указать поставку")
        Long supplyId,
        @FutureOrPresent(message = "Дата планирования должна быть будущей или текущей")
        LocalDate plannedDate,
        @NotNull(message = "Необходимо указать стоимость поставки.")
        BigDecimal supplyCost,
        @NotBlank(message = "Комиссия не может быть пустой")
        @NotNull(message = "Необходимо указать комиссию.")
        String commission,
        @Valid
        List<ProductPackagesRequest> productPackagesRequests,
        @Valid
        SupplyDeliveryRequest supplyDeliveryRequest
) {
}
