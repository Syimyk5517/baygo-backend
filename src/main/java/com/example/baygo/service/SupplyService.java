package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbb.FBBSupplyRequest;
import com.example.baygo.db.dto.request.fbb.SupplyWrapperRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.dto.response.supply.DeliveryDraftResponse;
import com.example.baygo.db.dto.response.supply.ProductBarcodeResponse;
import com.example.baygo.db.dto.response.supply.SupplySellerProductResponse;
import com.example.baygo.db.dto.response.supply.WarehouseResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.model.enums.SupplyType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface SupplyService {
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, Boolean isAscending, int page, int pageSize);

    PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);

    SupplyResponse getSupplyById(Long id);

    PaginationResponse<SupplyProductResponse> getSupplyProducts(Long supplyId, String keyWord, int page, int size);

    List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse);

    List<SupplyLandingPage> getAllSupplyForLanding();


    PaginationResponse<DeliveryDraftResponse> getDeliveryDrafts(int pageSize, int page);

    SimpleResponse deleteDeliveryDraft(Long supplyId);

    PaginationResponse<SupplySellerProductResponse> getSellerProducts(String searchWithBarcode, Long subCategoryId, int page, int pageSize);

    List<WarehouseResponse> getAllWarehouses();

    SimpleResponse createSupply(FBBSupplyRequest supplyRequest);

    List<WarehouseCostResponse> getAllWarehouseCost(Long warehouseId, SupplyType supplyType);

    List<ProductBarcodeResponse> getAllBarcodeProducts(Long supplyId);

    SimpleResponse willCompleteTheDelivery(SupplyWrapperRequest supplyWrapperRequest);


}
