package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.orders.OrderResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.model.enums.Status;
import com.example.baygo.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers/orders")
@RequiredArgsConstructor
@Tag(name = " Seller Orders")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerOrdersController {
    private final OrderService orderService;


    @GetMapping("/orders")
    public PaginationResponse<OrderResponse> getAllOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return orderService.getAllOrdersByFilter(page, size, keyword, status);

    }
}
