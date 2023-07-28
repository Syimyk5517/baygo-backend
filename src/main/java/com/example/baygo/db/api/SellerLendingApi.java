package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.db.service.AnswerOfSellerService;
import com.example.baygo.db.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seller_lending")
@RequiredArgsConstructor
@Tag(name = "Seller Lending API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerLendingApi {
    private final AnswerOfSellerService answerOfSellerService;
    private final ReviewService reviewService;

    @Operation(summary = "Get all questions", description = "This method will get all questions of buyer for seller")
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/questions")
    public List<QuestionForSellerLandingResponse> getAllQuestions() {
        return answerOfSellerService.getAllQuestionsForLandingOfSeller();
    }

    @Operation(summary = "Get all reviews", description = "This method will get all reviews of buyer")
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/reviews")
    public List<GetAllReviewsResponse> getAllReviews() {
        return reviewService.getAllReviewsForLandingOfSeller();
    }
}
