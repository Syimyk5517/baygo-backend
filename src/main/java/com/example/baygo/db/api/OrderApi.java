package com.example.baygo.db.api;

import com.example.baygo.db.enums.StatusOrder;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import com.example.baygo.db.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders api",description = "API for managing order organizations")
public class OrderApi {
private  final OrderService orderService;
@GetMapping("/search")
    PaginationResponse<OrderResponse>getAllOrders(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "6") int size,
                                                  @RequestParam(required = false) String keyWord,
                                                  @RequestParam (required = false) StatusOrder status){

    return orderService.getAll(page,size,keyWord,status);
}


}
