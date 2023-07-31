package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.DiscountRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/discounts")
@RequiredArgsConstructor
@Tag(name = "Discount Seller API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerDiscountController {
    private final DiscountService discountService;

    @Operation(summary = "Save a discount", description = "This method saves discount for subProducts!")
    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse saveDiscount(@RequestBody @Valid DiscountRequest discountRequest) {
        return discountService.saveDiscount(discountRequest);
    }
}
