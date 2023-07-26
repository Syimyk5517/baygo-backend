package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.OrderWareHouseResponse;
import com.example.baygo.db.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seller/landing")
@RequiredArgsConstructor
@Tag(name = "Landing Seller API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerLandingApi {
    private final OrderService orderService;
    @GetMapping("/orders_percentage")
    public List<OrderWareHouseResponse> getAllOrdersByWareHouse(){
        return orderService.getAllOrdersByWareHouse();

    }
}
