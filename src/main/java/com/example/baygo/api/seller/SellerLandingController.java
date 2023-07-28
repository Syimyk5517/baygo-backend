package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.SupplyLandingPage;
import com.example.baygo.service.OrderService;
import com.example.baygo.service.SupplyService;
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
@RequestMapping("/api/seller/landing")
@RequiredArgsConstructor
@Tag(name = "Seller landing page")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerLandingController {
    private final OrderService orderService;
    private final SupplyService supplyService;

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
}
