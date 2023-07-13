package com.example.baygo.db.api;

import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import com.example.baygo.db.responses.SimpleResponse;
import com.example.baygo.db.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders api", description = "API for managing order organizations")
public class OrderApi {
    private final OrderService orderService;

    @GetMapping("/search")
    PaginationResponse<OrderResponse> getAllOrders(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "6") int size,
                                                   @RequestParam(required = false) String keyWord,
                                                   @RequestParam(required = false) Status status) {
        return orderService.getAll(page, size, keyWord, status);
    }

    @DeleteMapping("/orderId")
    SimpleResponse deleteOrder(@RequestParam Long orderId) {
        return orderService.deleteById(orderId);
    }

    @GetMapping("/analysis")
    AnalysisResponse weeklyAnalysisOrder(@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, @RequestParam(required = false) Long warehouseId,@RequestParam(required = false) String nameOfTime,@RequestParam(required = false) boolean commission) {
        return orderService.getWeeklyAnalisys(startDate, endDate, warehouseId,nameOfTime,commission);
    }
}
