package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.BGWarehouseResponse;
import com.example.baygo.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/bg-warehouses")
@RequiredArgsConstructor
@Tag(name = "Seller supplier")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class BGWarehouseController {
    private final WarehouseService warehouseService;

    @Operation(summary = "Get all warehouses of BG for seller", description = "This to get all warehouses of BG for seller")
    @GetMapping
    List<BGWarehouseResponse> getAllBGWarehouses(){
        return warehouseService.getAllBGWarehouses();
    }
}
