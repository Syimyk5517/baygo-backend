package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.request.fbs.AccessCardRequest;
import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WareHouseRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.FbsWareHouseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fbsSeller/wareHouse")
@RequiredArgsConstructor
@Tag(name = "WareHouse and Access card Api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('SELLER')")
public class WareHouseController {

    private final FbsWareHouseService wareHouseService;


    @PostMapping()
    public SimpleResponse saveWareHouse(@RequestBody WareHouseRequest wareHouseRequest){
        return wareHouseService.saveWarehouse(wareHouseRequest);
    }
    @PostMapping("/access")
    public SimpleResponse saveAccessCard(@RequestBody AccessCardRequest accessCardRequest){
        return wareHouseService.saveAccess(accessCardRequest);
    }
    @PostMapping("/shipment")
    public SimpleResponse shippingMethod(@RequestBody ShipmentRequest shipmentRequest){
        return wareHouseService.saveShippMethod(shipmentRequest);
    }
}
