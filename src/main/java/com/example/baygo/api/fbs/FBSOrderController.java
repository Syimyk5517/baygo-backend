package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.fbs.FBSOrdersResponse;
import com.example.baygo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fbs/orders")
@RequiredArgsConstructor
@Tag(name = "FBS Orders")
@PreAuthorize("hasAnyAuthority('SELLER')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FBSOrderController {
    private final OrderService orderService;

    @Operation(summary = "Get all new fbs sellers orders", description = "New fbs orders with search and pagination")
    @GetMapping
    public PaginationResponse<FBSOrdersResponse> getAllFbsOrder(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ) {
        return orderService.getAllFbsOrdersOnPending(page, size, keyword);
    }
}
