package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
@Tag(name = "Add Basket API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerBasketController {
    private final BasketService basketService;
    @Operation(summary = "Add to basket or delete", description = "This method added or delete product to basket")
    @PostMapping("/{sizeId}")
    @PreAuthorize("hasAuthority('BUYER')")
    public SimpleResponse addToBasketOrDelete(@PathVariable Long sizeId){
        return basketService.addToBasketOrDelete(sizeId);
    }
    @GetMapping("/products")
    @Operation(summary = "Get all from basket", description = "This method get all products from basket")
    @PreAuthorize("hasAnyAuthority('BUYER')")
    public List<ProductsInBasketResponse> getAllProductsFromBasket(){
        return basketService.getAllProductsFromBasket();
    }
    @DeleteMapping
    @Operation(summary = "Delete all from basket", description = "This method delete all products from basket")
    @PreAuthorize("hasAnyAuthority('BUYER')")
    public SimpleResponse deleteAllFromBasket(){
        return basketService.deleteAllFromBasket();
    }
}
