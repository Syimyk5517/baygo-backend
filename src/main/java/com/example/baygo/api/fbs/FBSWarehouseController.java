package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.request.fbs.WarehouseRequest;
import com.example.baygo.db.dto.response.SellerFBSWarehouseResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.FBSWareHouseAddProduct;
import com.example.baygo.db.dto.response.fbs.ProductGetAllResponse;
import com.example.baygo.service.FBSSupplyService;
import com.example.baygo.service.FbsWareHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fbs/warehouses")
@RequiredArgsConstructor
@Tag(name = "FBS WareHouse and Access card api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class FBSWarehouseController {
    private final FbsWareHouseService wareHouseService;
    private final FBSSupplyService supplyService;

    @Operation(summary = "Get all seller's warehouses", description = "This method will get all warehouses of seller")
    @GetMapping
    public List<SellerFBSWarehouseResponse> getSellerFBSWarehouses() {
        return wareHouseService.getSellerFBSWarehouses();
    }

    @Operation(summary = "Save fbs warehouse", description = "The method will save fbs warehouse")
    @PostMapping
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
    @GetMapping("/products")
    public List<ProductGetAllResponse> getAllProduct(
            @RequestParam Long wareHouseId,
            @RequestParam(required = false) String keyWord) {
        return wareHouseService.getAllProduct(wareHouseId, keyWord);
    }

    @Operation(summary = "Add quantity to FBS products", description = "This method will add quantity to FBS products")
    @PostMapping("/add-quantity")
    public SimpleResponse addQuantityToFbsProducts(@RequestBody @Valid SupplyRequest supplyRequest) {
        return supplyService.saveSupply(supplyRequest);
    }
}