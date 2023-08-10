package com.example.baygo.api.buyer;

import com.example.baygo.db.model.Banner;
import com.example.baygo.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buyer/banners")
@RequiredArgsConstructor
@Tag(name = "Banner Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerBannerController {
    private final BannerService bannerService;
    @Operation(summary = "Get all banners",description = "This method gets all banners")
    @GetMapping
    @PermitAll
    public List<Banner> getAllBanners() {
        return bannerService.getAllBanners();
    }

}
