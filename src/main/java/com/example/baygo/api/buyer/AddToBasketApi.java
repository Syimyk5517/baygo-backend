package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.AddToBasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/add_basket")
@RequiredArgsConstructor
@Tag(name = "Add Basket API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AddToBasketApi {
    private final AddToBasketService addToBasketService;
    @Operation(summary = "Add to basket", description = "This method added product to basket")
    @PostMapping("{sizeId}")
    @PreAuthorize("hasAuthority('BUYER')")
    public SimpleResponse addToBasket(@PathVariable Long sizeId){
        return addToBasketService.addToBasket(sizeId);
    }
    @GetMapping("/get_all_products")
    @Operation(summary = "Get all from basket", description = "This method get all products from basket")
    @PreAuthorize("hasAnyAuthority('BUYER')")
    public List<ProductsInBasketResponse> getAllProductsFromBasket(){
        return addToBasketService.getAllProductsFromBasket();
    }
    @DeleteMapping("{sizeId}")
    @Operation(summary = "Delete from basket", description = "This method delete product from basket")
    @PreAuthorize("hasAnyAuthority('BUYER')")
    public SimpleResponse deleteFromBasket(@PathVariable Long sizeId){
        return addToBasketService.deleteFromBasket(sizeId);
    }
}
