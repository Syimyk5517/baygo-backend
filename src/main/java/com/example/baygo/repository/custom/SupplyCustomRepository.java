package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;

import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.dto.response.supply.SupplySellerProductResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.model.enums.SupplyType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SupplyCustomRepository {
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status, int page, int pageSize);
    PaginationResponse<SupplyProductResponse> getSupplyProducts(Long sellerId, Long supplyId, String keyWord, int page, int size);

    PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);

    List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse);

    List<SupplyLandingPage> getAllSupplyForLanding(Long sellerId);
    PaginationResponse<SupplySellerProductResponse> getSellerProducts(Integer searchWithBarcode, String searchWithCategory,
                                                                      String searchWithBrand, int page, int pageSize);
    List<WarehouseCostResponse> getAllWarehouseCostResponse(Long warehouseId, SupplyType supplyType);

}