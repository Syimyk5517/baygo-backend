package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.request.BuyerProfileImageRequest;
import com.example.baygo.db.dto.request.BuyerProfileRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.BuyerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer/profile")
@RequiredArgsConstructor
@Tag(name = "Profile Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProfileBuyerController {
    private final BuyerProfileService buyerProfileService;
    @Operation(summary = "Update profile!",description = "This method updates profile!")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('BUYER')")
    public SimpleResponse updateProfile(@RequestBody @Valid BuyerProfileRequest request) {
        return buyerProfileService.updateProfile(request);
    }

    @Operation(summary = "Update buyer profile image!", description = "This method updates buyer profile image!")
    @PutMapping("/image")
    @PreAuthorize("hasAnyAuthority('BUYER')")
    public SimpleResponse updateProfileImage(@RequestBody @Valid BuyerProfileImageRequest request) {
        return buyerProfileService.updateProfileImage(request);
    }

    @Operation(summary = "Delete buyer profile!",description = "This method deletes buyer profile!!!")
    @DeleteMapping

    public SimpleResponse deleteProfile(){
        return buyerProfileService.deleteProfile();
    }
}
