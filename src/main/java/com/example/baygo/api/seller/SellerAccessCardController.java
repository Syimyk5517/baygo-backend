package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.SupplierRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.AccessCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/carPass")
@RequiredArgsConstructor
@Tag(name = "Seller supplier")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerAccessCardController {

    private final AccessCardService accessCardService;

    @Operation(summary = "Create the supplier", description = "This method creating the supplier")
    @PostMapping("/{supplyId}")
    SimpleResponse create(
            @PathVariable Long supplyId,
            @RequestBody SupplierRequest supplierRequest){
        return accessCardService.save(supplierRequest, supplyId);
    }
}
