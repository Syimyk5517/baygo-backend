package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
@Tag(name = "Supply API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupplyApi {
    private final SupplyService service;

    @PreAuthorize("hasAuthority('SELLER')")
    @Operation(summary = "Get all supplies of seller", description = "This method retrieves all supplies associated with a seller.")
    @GetMapping
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller
            (@RequestParam(required = false) String supplyNumber,
             @RequestParam(required = false) SupplyStatus status,
             @RequestParam(required = false, defaultValue = "1") int page,
             @RequestParam(required = false, defaultValue = "15") int pageSize) {
        return service.getAllSuppliesOfSeller(supplyNumber, status, page, pageSize);
    }

    @Operation(summary = "Get All and search supplies products!!", description = "This method get all and search supplies")
    @GetMapping("/search/get_all_")
    @PreAuthorize("hasAuthority('SELLER')")
    public PaginationResponse<SupplyProductResponse> getAllAndSearch(@RequestParam(required = false) String keyWorld, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "8") int size) {
        return supplyService.searchSupplyProducts(keyWorld, page, size);
    }

    @Operation(summary = "Get supply by id ", description = "This method gets the get supply by products")
    @GetMapping("/get_supply_by/{id}")
    @PreAuthorize("hasAuthority('SELLER')")
    public SupplyResponse getById(@PathVariable Long id) {
        return supplyService.getSupplyById(id);
    }
}
