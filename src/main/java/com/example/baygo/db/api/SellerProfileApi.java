package com.example.baygo.db.api;

import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/profile")
@RequiredArgsConstructor
@Tag(name = "Seller Profile API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerProfileApi {
    private final SellerService service;

    @Operation(summary = "Update seller profile", description = "This is update seller profile method")
    @PutMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse updateSellerProfile(@RequestBody SellerProfileRequest request) {
        return service.updateSellerProfile((request));
    }

    @Operation(summary = "Update seller store profile", description = "This is update seller store profile method")
    @PutMapping("/store")
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse updateSellerStoreProfile(@RequestBody SellerStoreInfoRequest request) {
        return service.updateSellerStoreInfo(request);
    }

    @Operation(summary = "Update logo of store profile", description = "This method to update seller store logo profile")
    @PutMapping("/logo")
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse updateLogoOfStore(@RequestParam String newLogo) {
        return service.updateLogoOfStore(newLogo);
    }
}