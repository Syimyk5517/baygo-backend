package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.FavoriteResponse;
import com.example.baygo.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "Buyer favorites", description = "Favorites goods and brands")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('BUYER')")
public class BuyerFavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "Get favorites", description = "This method gets all favorites")
    public PaginationResponse<FavoriteResponse> getAllFavorites(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size) {
        return favoriteService.getAllFavorProduct(page, size, search);
    }

    @PostMapping("/toggle")
    @Operation(summary = "Add and delete product from favorite", description = "This method will add  and delete product from favorites")
    public SimpleResponse toggleFavorite(@RequestParam Long subProductId) {
        return favoriteService.toggleFavorite(subProductId);

    }

    @DeleteMapping
    @Operation(summary = "Clear all product from favorite", description = "This method will clear")
    public SimpleResponse clearFavorite() {
        return favoriteService.deleteFavor();
    }
}
