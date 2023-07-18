package com.example.baygo.db.api;

import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller/profile")
@RequiredArgsConstructor
@Tag(name = "Seller Profile API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerProfileApi {
    private final SellerService service;
    @Operation(summary = "Update seller profile", method = "This is create seller profile method")
    @PutMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse createSellerProfile(SellerProfileRequest request){
       return service.sellerProfile(request);
    }
    @Operation(summary = "Update seller store profile", method = "This is create seller profile method")
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse updateSellerStoreProfile(SellerStoreInfoRequest request){
        return service.sellerStoreInfo(request);
    }
}
