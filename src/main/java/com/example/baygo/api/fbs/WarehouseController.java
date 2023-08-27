package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WarehouseRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.FBSWareHouseAddProduct;
import com.example.baygo.db.dto.response.fbs.ProductGetAllResponse;
import com.example.baygo.service.FbsWareHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fbs/warehouses")
@RequiredArgsConstructor
@Tag(name = "WareHouse and Access card Api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('SELLER')")
public class WarehouseController {
    private final FbsWareHouseService wareHouseService;

    @Operation(summary = "Save fbs warehouse", description = "The method will save fbs warehouse")
    @PostMapping()
    public SimpleResponse saveWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        return wareHouseService.saveWarehouse(warehouseRequest);
    }

    @Operation(summary = "Save shipment of warehouse", description = "The method will save shipment of warehouse")
    @PostMapping("/shipment-method")
    public SimpleResponse shippingMethod(@RequestBody @Valid ShipmentRequest shipmentRequest) {
        return wareHouseService.saveShippingMethod(shipmentRequest);
    }

    @Operation(summary = "Add product to own warehouse", description = "The method will add product to warehouse")
    @PostMapping("/add-product")
    public SimpleResponse addProduct(@RequestBody FBSWareHouseAddProduct fbsWareHouseAddProduct) {
        return wareHouseService.addProduct(fbsWareHouseAddProduct);
    }

    @Operation(summary = "Get all products of warehouse", description = "The method will get all products")
    @GetMapping("/{wareHouseId}")
    public List<ProductGetAllResponse> getAllProduct(@RequestParam Long wareHouseId) {
        return wareHouseService.getAllProduct(wareHouseId);
    }
}
