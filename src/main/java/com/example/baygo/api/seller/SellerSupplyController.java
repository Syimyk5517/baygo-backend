package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.PackingRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.service.PackingService;
import com.example.baygo.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
@Tag(name = "Seller supply")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerSupplyController {
    private final SupplyService service;
    private final PackingService packingService;

    @Operation(summary = "Get all supplies of seller", description = "This method retrieves all supplies associated with a seller.")
    @GetMapping
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller
            (@RequestParam(required = false) String supplyNumber,
             @RequestParam(required = false) SupplyStatus status,
             @RequestParam(defaultValue = "1") int page,
             @RequestParam(defaultValue = "15") int pageSize) {
        return service.getAllSuppliesOfSeller(supplyNumber, status, page, pageSize);
    }

    @Operation(summary = "Get all supply products", description = "This method to get all and search supply products")
    @GetMapping("/{supplyId}/products")
    public PaginationResponse<SupplyProductResponse> getSupplyProducts(
            @PathVariable Long supplyId,
            @RequestParam(required = false) String keyWord,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size) {
        return service.getSupplyProducts(supplyId, keyWord, page, size);
    }

    @Operation(summary = "Get supply by id ", description = "This method gets the get supply by products")
    @GetMapping("/{id}")
    public SupplyResponse getById(@PathVariable Long id) {
        return service.getSupplyById(id);
    }

    @Operation(summary = "repacking", description = "this is repackaging method")
    @PostMapping("{supplyId}")
    SimpleResponse packing(@PathVariable Long supplyId, @RequestBody List<PackingRequest> packingRequests){
        return packingService.repacking(supplyId, packingRequests);
    }

    @Operation(summary = "Delivery factor", description = "This method returns the acceptance coefficients")
    @GetMapping("/delivery_factor")
    public PaginationResponse<DeliveryFactorResponse> deliveryFactorResponses(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAllDeliveryFactor(keyword, date, size, page);
    }

    @Operation(summary = "Transit direction from warehouse.", description = "This method transit direction of product of warehouse.")
    @GetMapping("/transit_direction")
    public List<SupplyTransitDirectionResponse> getAllTransitDirections(
            @RequestParam(required = false) String transitWarehouse,
            @RequestParam(required = false) String destinationWarehouse) {
        return service.getAllTransitDirections(transitWarehouse, destinationWarehouse);
    }
}