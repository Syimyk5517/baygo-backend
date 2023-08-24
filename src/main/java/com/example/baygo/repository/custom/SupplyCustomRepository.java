package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SupplyLandingPage;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyTransitDirectionResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SupplyCustomRepository {
    PaginationResponse<SupplyProductResponse> getSupplyProducts(Long sellerId, Long supplyId, String keyWord, int page, int size);

    PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);

    List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse);

    List<SupplyLandingPage> getAllSupplyForLanding(Long sellerId);
}