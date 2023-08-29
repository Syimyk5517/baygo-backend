package com.example.baygo.api.seller;


import com.example.baygo.db.dto.request.AccessCardRequest;
import com.example.baygo.db.dto.request.fbs.SupplyAccessCardRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.AccessCardService;
import com.example.baygo.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/car_pass")
@RequiredArgsConstructor
@Tag(name = "Seller supplier")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerAccessCardController {

    private final AccessCardService accessCardService;
    private final WarehouseService warehouseService;

    @Operation(summary = "Create the supplier", description = "This method creating the supplier")
    @PostMapping("/{supplyId}")
    SimpleResponse create(
            @PathVariable Long supplyId,
            @RequestBody AccessCardRequest accessCardRequest){
        return accessCardService.save(accessCardRequest, supplyId);
    }

    @PostMapping("/access")
    public SimpleResponse saveAccessCard(@RequestBody SupplyAccessCardRequest accessCardRequest){
        return warehouseService.saveAccess(accessCardRequest);
    }
}
