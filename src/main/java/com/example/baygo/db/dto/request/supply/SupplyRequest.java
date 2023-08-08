package com.example.baygo.db.dto.request.supply;

import com.example.baygo.db.model.enums.SupplyType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
@Builder
public record SupplyRequest(
        List<SupplyChooseRequest> supplyChooseRequests,
        LocalDate plannedDate,
        Long warehouseId,
        SupplyType supplyType
) {
}
