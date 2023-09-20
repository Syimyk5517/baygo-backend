package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.request.ReviewByBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.product.ReviewGetByIdResponse;
import com.example.baygo.service.ReviewByBuyerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerReviewController {
    private final ReviewByBuyerService service;

    @Operation(summary = "Save Review", description = "This method saves reviews which written by Buyer!")
    @PostMapping
    @PreAuthorize("hasAuthority('BUYER')")
    public SimpleResponse saveReview(@RequestBody @Valid ReviewByBuyerRequest request) {
        return service.saveReview(request);
    }

    @Operation(summary = "Get all reviews by sub product_id")
    @GetMapping("/subProductId")
    @PreAuthorize("hasAuthority('BUYER')")
    public ReviewGetByIdResponse getReviewByProduct(@RequestParam Long subProductId, @RequestParam(required = false) boolean withImages) {
        return service.getAllReviewByProduct(subProductId, withImages);
    }
}
