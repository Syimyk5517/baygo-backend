package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.FavoriteResponse;
import com.example.baygo.service.BuyerProfileService;
import com.example.baygo.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "Buyer favorites", description = "Favorites goods and brands")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('BUYER')")
public class BuyerFavoriteApi {
    private final FavoriteService favoriteService;
    private final BuyerProfileService buyerProfileService;

    @GetMapping
    @Operation(summary = "Get favorites", description = "This method gets all favorites")
    public PaginationResponse<FavoriteResponse> getAllFavorites(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocalDate createDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return favoriteService.getAllFavorProduct(page, size, search, createDate);
    }

    @PostMapping("/toggle")
    @Operation(summary = "Add product to favorite", description = "This method will add product to favorites")
    public SimpleResponse toggleFavorite(@RequestParam Long subProductId) {
        return buyerProfileService.toggleFavorite(subProductId);

    }

    @DeleteMapping
    @Operation(summary = "Clear all product from favorite", description = "This method will clear")
    public SimpleResponse clearFavorite() {
        return buyerProfileService.deleteFavor();
    }

    @DeleteMapping("/{subProductId}")
    @Operation(summary = "Remove product from favorites", description = "This method will cleear only one")
    public SimpleResponse removeProduct(@PathVariable Long subProductId) {
        return buyerProfileService.removeProduct(subProductId);
    }
}
