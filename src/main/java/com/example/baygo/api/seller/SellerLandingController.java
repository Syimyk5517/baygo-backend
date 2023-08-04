package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.orders.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.orders.RecentOrdersResponse;
import com.example.baygo.service.AnswerOfSellerService;
import com.example.baygo.service.OrderService;
import com.example.baygo.service.ReviewService;
import com.example.baygo.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/landing")
@RequiredArgsConstructor
@Tag(name = "Seller landing page")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerLandingController {
    private final OrderService orderService;
    private final SupplyService supplyService;
    private final AnswerOfSellerService answerOfSellerService;
    private final ReviewService reviewService;

    @Operation(summary = "Get percentage orders of locations", description = "This method will get all orders percentage divide into ware house location")
    @GetMapping("/orders_percentage")
    public List<OrderWareHouseResponse> getAllOrdersByWareHouse() {
        return orderService.getAllOrdersByWareHouse();

    }

    @Operation(summary = "Get all supply for landing", description = "This method will get all supply for landing")
    @GetMapping("/supplies")
    public List<SupplyLandingPage> getAllSupplyForLanding() {
        return supplyService.getAllSupplyForLanding();
    }

    @Operation(summary = "Get all questions", description = "This method will get all questions of buyer for seller")
    @GetMapping("/questions")
    public List<QuestionForSellerLandingResponse> getAllQuestions() {
        return answerOfSellerService.getAllQuestionsForLandingOfSeller();
    }

    @Operation(summary = "Get all reviews", description = "This method will get all reviews of buyer")
    @GetMapping("/reviews")
    public List<GetAllReviewsResponse> getAllReviews() {
        return reviewService.getAllReviewsForLandingOfSeller();
    }

    @Operation(summary = "Get recent orders", description = "This method to get recent orders")
    @GetMapping("/recent_orders")
    List<RecentOrdersResponse> getRecentOrders() {
        return orderService.getResentOrders();
    }
}
