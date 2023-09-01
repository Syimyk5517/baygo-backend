package com.example.baygo.db.dto.request.fbb;

import com.example.baygo.db.model.enums.SupplyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record FBBSupplyRequest(
        @NotNull(message = "Идентификатор склада не может быть пустым")
        Long warehouseId,
        @FutureOrPresent(message = "Дата планирования должна быть будущей или текущей")
        LocalDate plannedDate,
        @NotNull(message = "Тип упаковки не может быть пустым")
        SupplyType supplyType,
        @Valid
        List<SupplyChooseRequest> supplyChooseRequests
) {
}
