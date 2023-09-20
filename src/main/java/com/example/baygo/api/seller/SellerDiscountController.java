package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.DiscountRequest;
import com.example.baygo.db.dto.request.DiscountRequestForCancel;
import com.example.baygo.db.dto.response.CalendarActionResponse;
import com.example.baygo.db.dto.response.DiscountProductResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @Operation(summary = "Get all discounts on calendars", description = "This is a method to get all discounts on calendars")
    @GetMapping("/calendar_action")
    @PreAuthorize("hasAuthority('SELLER')")
    public List<CalendarActionResponse> getAllDiscount(@RequestParam(required = false) LocalDate date) {
        return discountService.getAllDiscount(date);
    }

    @Operation(summary = "Get all products for discount", description = "This is a method to get all products for discount with status")
    @GetMapping("/products")
    @PreAuthorize("hasAuthority('SELLER')")
    public PaginationResponse<DiscountProductResponse> getAllProducts(
            @RequestParam(defaultValue = "false") boolean isForCancel,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return discountService.getAllProducts(isForCancel, page, size);
    }

    @Operation(summary = "Cancellation of discount by subProducts supplyId!", description = "This method cancelled of discount by subProducts supplyId!")
    @DeleteMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse cancellationOfDiscount(@RequestBody @Valid DiscountRequestForCancel request) {
        return discountService.cancellationOfDiscount(request);
    }
}
