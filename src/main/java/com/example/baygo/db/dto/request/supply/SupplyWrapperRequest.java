package com.example.baygo.db.dto.request.supply;

import com.example.baygo.db.dto.request.supply.ProductPackagesRequest;
import com.example.baygo.db.dto.request.supply.SupplyDeliveryRequest;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Builder
public record SupplyWrapperRequest(
        Long supplyId,
        LocalDate plannedDate,
        BigDecimal supplyCost,
        String commission,
        List<ProductPackagesRequest> productPackagesRequests,
        SupplyDeliveryRequest supplyDeliveryRequest
) {
}
