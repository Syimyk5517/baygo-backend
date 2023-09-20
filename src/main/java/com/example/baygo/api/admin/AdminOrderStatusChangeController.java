package com.example.baygo.api.admin;

import com.example.baygo.db.dto.request.admin.AdminOrderStatusChangeRequest;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.admin.AdminFBBOrderResponse;
import com.example.baygo.db.dto.response.admin.AdminFBSOrderResponse;
import com.example.baygo.db.model.enums.OrderStatus;
import com.example.baygo.service.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@Tag(name = "Admin Orders Status Change Api")
@CrossOrigin(value = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminOrderStatusChangeController {
    private final AdminOrderService adminOrderService;

    @Operation(summary = "Order status multiple change", description = "This method will change status of orders")
    @PostMapping("/status-change")
    public SimpleResponse orderStatusChange(@RequestBody AdminOrderStatusChangeRequest orderStatusChangeRequest) {
        return adminOrderService.statusChange(orderStatusChangeRequest.getOrderIds(), orderStatusChangeRequest.getOrderStatus());
    }

    @Operation(summary = "Get all orders status", description = "This method will get all status of fbs supplies")
    @GetMapping("/statuses")
    public List<OrderStatus> getAllStatus() {
        return adminOrderService.getAllStatus();
    }

    @Operation(summary = "Get all fbb orders", description = "This method will get all fbb orders")
    @GetMapping("/fbb")
    public PaginationResponse<AdminFBBOrderResponse> getAllFBBOrder(@RequestParam(required = false) String keyWord,
                                                                    @RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return adminOrderService.getAllFBBOrders(keyWord, page, size);
    }

    @Operation(summary = "Count total FBB order quantity")
    @GetMapping("/fbb-quantity")
    public long countTotalFBBOrderQuantity() {
        return adminOrderService.countFBBOrder();
    }

    @Operation(summary = "Get all fbs orders", description = "This method will get all fbs orders")
    @GetMapping("/fbs")
    public PaginationResponse<AdminFBSOrderResponse> getAllFBSOrder(@RequestParam(required = false) String keyWord,
                                                                    @RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return adminOrderService.getAllFBSOrders(keyWord, page, size);
    }
    @Operation(summary = "Count total FBS order quantity")
    @GetMapping("/fbs-quantity")
    public long countTotalFBSOrderQuantity() {
        return adminOrderService.countFBSOrder();
    }
}
