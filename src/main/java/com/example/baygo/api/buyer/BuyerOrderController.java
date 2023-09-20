package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.request.order.BuyerOrderRequest;
import com.example.baygo.db.dto.response.BuyerOrderHistoryDetailResponse;
import com.example.baygo.db.dto.response.BuyerOrdersHistoryResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @GetMapping
    @PreAuthorize("hasAuthority('BUYER')")
    public List<BuyerOrdersHistoryResponse> getAllHistoryOfOrder(@RequestParam(required = false) String keyWord){
        return orderService.getAllHistoryOfOrder(keyWord);
    }

    @Operation(summary = "Get history detail of order by supplyId!", description = "This method gets history detail of order by order supplyId!")
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('BUYER')")
    public BuyerOrderHistoryDetailResponse getHistoryOfOrderByOrderId(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }
    @Operation(summary = "Buyer's order",description = "This is the method for ordering")
    @PostMapping
    @PreAuthorize("hasAuthority('BUYER')")
    public SimpleResponse saveBuyerOrder(@RequestBody @Valid BuyerOrderRequest buyerOrderRequest){
       return orderService.saveBuyerOrder(buyerOrderRequest);
    }
}
