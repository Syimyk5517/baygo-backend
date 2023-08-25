package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.PackingRequest;
import com.example.baygo.db.dto.request.fbs.SupplyAccessCardRequest;
import com.example.baygo.db.dto.request.AccessCardRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/seller/reviews")
@RequiredArgsConstructor
@Tag(name = "Seller review")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Get All Review", description = "This method getAll Review ,search,pagination. IsAnswered: false - to get unanswered reviews, true - to get archive of reviews.")
    @GetMapping
    public PaginationReviewAndQuestionResponse<ReviewResponse> getAllReviews(
            @RequestParam(required = false) String keyWord,
            @RequestParam(defaultValue = "false") boolean isAnswered,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "8") int pageSize) {
        return reviewService.getAllReviews(keyWord, isAnswered, page, pageSize);
    }
}
