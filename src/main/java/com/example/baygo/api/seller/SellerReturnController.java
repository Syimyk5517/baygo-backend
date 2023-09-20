package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.seller.SellerReturnGetByIdResponse;
import com.example.baygo.db.dto.response.seller.SellerReturnResponse;
import com.example.baygo.service.SellerReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/returns")
@RequiredArgsConstructor
@Tag(name = "Seller returns")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerReturnController {
    private final SellerReturnService returnService;

    @Operation(summary = "Get all returns", description = "This method will get all returns of seller")
    @GetMapping
    public List<SellerReturnResponse> getAllReturns() {
        return returnService.getAllReturns();
    }

    @Operation(summary = "Return get by supplyId", description = "This method will get return by supplyId")
    @GetMapping("/{returnId}")
    public SellerReturnGetByIdResponse getById(@PathVariable Long returnId) {
        return returnService.getById(returnId);
    }
}
