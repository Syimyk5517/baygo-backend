package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyResponse;
import com.example.baygo.db.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
@Tag(name = "Supplies API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupplyApi {
    private final SupplyService supplyService;

    @Operation(summary = "Supply get by id", description = "This method to get supply by id.")
    @GetMapping("/get/supply/by/id")
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    public SupplyResponse getSupplyById(Long id) {
        return supplyService.getSupplyById(id);
    }

    @Operation(summary = "Supplies get all", description = "This method to get supplies get all and search.")
    @GetMapping("/get/all/supplies")
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    public List<SupplyProductResponse> getAllSupplyProducts(String keyWord, int page, int size) {
        return supplyService.searchSupplyProducts(keyWord, page, size);
    }
}
