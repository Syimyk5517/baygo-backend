package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.fbs.OrdersResponse;
import com.example.baygo.db.model.enums.OrderStatus;
import com.example.baygo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fbs/orders")
@RequiredArgsConstructor
@Tag(name = "FBS Orders")
@PreAuthorize("hasAnyAuthority('SELLER')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FbsOrderController {
    private final OrderService orderService;

    @Operation(summary = "Get all fbs sellers orders", description = "All orders with search,filter and pagination")
    @GetMapping
    public PaginationResponse<OrdersResponse> getAllFbsOrder(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) String keyword
    ) {
        return orderService.getAllFbsOrders(page, size, keyword, orderStatus);
    }
}
