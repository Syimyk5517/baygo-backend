package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/supply")
@RequiredArgsConstructor
@Tag(name = "Supply API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupplyApi {
    private final SupplyService service;

    @Operation(summary = "Get all supplies of seller", description = "This method retrieves all supplies associated with a seller.")
    @GetMapping
    @PreAuthorize("hasAuthority('SELLER')")
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller
            (@RequestParam(required = false) String supplyNumber,
             @RequestParam(required = false) SupplyStatus status,
             @RequestParam(required = false, defaultValue = "1") int page,
             @RequestParam(required = false, defaultValue = "15") int pageSize) {
        return service.getAllSuppliesOfSeller(supplyNumber, status, page, pageSize);
    }

    @GetMapping("/coefficients/acceptance")
    @PreAuthorize("hasAuthority('SELLER')")
    @Operation(summary = "Delivery factor", description = "this method returns the acceptance coefficients")
    public List<DeliveryFactorResponse> deliveryFactorResponses(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) LocalDate date,
            @RequestParam int size, @RequestParam int page) {
        return service.findAllDeliveryFactor(keyword, date, size, page);
    }
}
