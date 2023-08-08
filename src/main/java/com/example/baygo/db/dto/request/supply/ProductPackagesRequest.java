package com.example.baygo.db.dto.request.supply;

import com.example.baygo.db.dto.request.supply.NumberOfProductsRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductPackagesRequest(
     int packageNumber,
     List<NumberOfProductsRequest> numberOfProductsRequests


) {
}
