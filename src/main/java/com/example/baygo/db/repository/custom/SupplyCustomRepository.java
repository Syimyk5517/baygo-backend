package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.SupplyLandingPage;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.dto.response.SupplyTransitDirectionResponse;

import java.time.LocalDate;
import java.util.List;

public interface SupplyCustomRepository {
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status, int page, int pageSize);

    PaginationResponse<SupplyProductResponse> getSupplyProducts(Long sellerId, Long supplyId, String keyWord, int page, int size);

    PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);

    List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse);

    List<SupplyLandingPage> getAllSupplyForLanding(Long sellerId);
}