package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.seller.AdvertisementSaveRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/promotion")
@RequiredArgsConstructor
@Tag(name = "Seller promotion")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerAdvertisementController {
    private final AdvertisementService advertisementService;
    @Operation(summary = "Save of promotion",description = "This method will save promotion of seller")
    @PostMapping
    public SimpleResponse saveAdvertisement(@Valid @RequestBody AdvertisementSaveRequest saveRequest){
        return advertisementService.saveAdvertisement(saveRequest);
    }
}
