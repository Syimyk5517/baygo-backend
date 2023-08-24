package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WarehouseRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.FbsWareHouseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fbs/warehouses")
@RequiredArgsConstructor
@Tag(name = "WareHouse and Access card Api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('SELLER')")
public class WarehouseController {
    private final FbsWareHouseService wareHouseService;

    @PostMapping()
    public SimpleResponse saveWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest){
        return wareHouseService.saveWarehouse(warehouseRequest);
    }

    @PostMapping("/shipment-method")
    public SimpleResponse shippingMethod(@RequestBody @Valid ShipmentRequest shipmentRequest){
        return wareHouseService.saveShippingMethod(shipmentRequest);
    }
}
