package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.enums.SupplyStatus;

import java.time.LocalDate;
import java.util.List;

public interface SupplyService {
    List<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, int page, int pageSize);

}
