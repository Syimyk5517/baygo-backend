package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.service.AddBasketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/add_basket")
@RequiredArgsConstructor
@Tag(name = "Add Basket API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AddBasketApi {
    private final AddBasketService servise;
    public SimpleResponse addToBasket(AddBasketRequest request){
        return null;
    }
}
