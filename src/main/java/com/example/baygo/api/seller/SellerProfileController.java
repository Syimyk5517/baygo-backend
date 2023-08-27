package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SellerProfileResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/profile")
@RequiredArgsConstructor
@Tag(name = "Seller profile")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerProfileController {
    private final SellerService service;

    @Operation(summary = "Get seller profile", description = "This is get seller profile method")
    @GetMapping
    public SellerProfileResponse getSellerProfile() {
        return service.getSellerProfile();
    }

    @Operation(summary = "Update seller profile", description = "This is update seller profile method")
    @PutMapping
    public SimpleResponse updateSellerProfile(@RequestBody SellerProfileRequest request) {
        return service.updateSellerProfile((request));
    }

    @Operation(summary = "Update seller store profile", description = "This is update seller store profile method")
    @PutMapping("/store")
    public SimpleResponse updateSellerStoreProfile(@RequestBody SellerStoreInfoRequest request) {
        return service.updateSellerStoreInfo(request);
    }

    @Operation(summary = "Update logo of store profile", description = "This method to update seller store logo profile")
    @PutMapping("/logo")
    public SimpleResponse updateLogoOfStore(@RequestParam String newLogo) {
        return service.updateLogoOfStore(newLogo);
    }
}