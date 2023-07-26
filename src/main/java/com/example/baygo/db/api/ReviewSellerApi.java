package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import com.example.baygo.db.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Seller API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewSellerApi {

//    private final ReviewService reviewService;
//
//    @GetMapping
//    @Operation(summary = "Get All Review", description = "This method getAll Review ,search,pagination")
//    @PreAuthorize("hasAuthority('SELLER')")
//    public PaginationReviewResponse<ReviewResponse> getAllReview(
//            @RequestParam(required = false) String keyWord,
//            @RequestParam(required = false ,defaultValue = "1") int page,
//            @RequestParam(required = false ,defaultValue = "4") int pageSize){
//        return reviewService.getAllReviews(keyWord,page,pageSize);
//    }
}
