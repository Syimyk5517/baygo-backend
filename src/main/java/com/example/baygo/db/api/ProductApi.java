package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductApi {
    private final ProductService service;

    @GetMapping
    public List<ProductResponseForSeller> getAllProductForSeller(@RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "11") int size){
        return service.findAll(PageRequest.of(page,size));
    }
}
