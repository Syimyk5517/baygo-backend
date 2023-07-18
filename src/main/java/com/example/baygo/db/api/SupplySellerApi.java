package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/seller/supply")
@RequiredArgsConstructor
@Tag(name = "Supplies")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupplySellerApi {
    private final SupplyService supplyService;

    @GetMapping("/coefficients/acceptance")
    @Operation(summary = "Delivery factor", description = "this method returns the acceptance coefficients")
    public List<DeliveryFactorResponse> deliveryFactorResponses(
            @RequestParam(required = false) String keyword, @RequestParam(required = false) LocalDate date,
            @RequestParam int size, @RequestParam int page) {
        return supplyService.findAllDeliveryFactor(keyword, date, size, page);
    }
}