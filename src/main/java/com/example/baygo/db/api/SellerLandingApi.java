package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.RecentOrdersResponse;
import com.example.baygo.db.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/landing")
@RequiredArgsConstructor
@Tag(name = "Landing Seller API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerLandingApi {
    private final OrderService orderService;

    @Operation(summary = "Get recent orders", description = "This method get recent orders")
    @GetMapping("/get_recent_orders")
    @PreAuthorize("hasAuthority('SELLER')")
    List<RecentOrdersResponse> getRecentOrders(@RequestParam(required = false) Long sellerId) {
        return orderService.getResentOrders(sellerId);
    }
}
