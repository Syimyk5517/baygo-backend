package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.product.ProductGetByIdResponse;
import com.example.baygo.service.SubProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Buyer/get_by_id")
@RequiredArgsConstructor
@Tag(name = "Product get by id Api")
@CrossOrigin(origins = "*", maxAge = 3600)

public class ProductBuyerController {
    private final SubProductService subProductService;

    @GetMapping("/sub_productId")
    public ProductGetByIdResponse getById(@RequestParam Long subProductId) {
        return subProductService.getById(subProductId);
    }


}
