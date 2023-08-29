package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.BuyerOrderHistoryDetailResponse;
import com.example.baygo.db.dto.response.BuyerOrdersHistoryResponse;
import com.example.baygo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer/orders")
@RequiredArgsConstructor
@Tag(name = "Order Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerOrderController {
    private final OrderService orderService;
    @Operation(summary = "Get all history of order!", description = "This method gets all history of order!")
    @GetMapping("/get_all")
    @PreAuthorize("hasAuthority('BUYER')")
    public List<BuyerOrdersHistoryResponse> getAllHistoryOfOrder(){
        return orderService.getAllHistoryOfOrder();
    }

    @Operation(summary = "Get all history of order!", description = "This method gets all history of order!")
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('BUYER')")
    public BuyerOrderHistoryDetailResponse getHistoryOfOrderByOrderId(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }
}
