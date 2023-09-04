package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.HomePageResponse;
import com.example.baygo.service.HomePageService;
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
@RequestMapping("/api/buyer/home_page")
@RequiredArgsConstructor
@Tag(name = "Home Page API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomePageController {
    private final HomePageService homePageService;

    @Operation(summary = "Get bestseller products!", description = "This method gets bestseller products for home page!")
    @GetMapping("/bestsellers")
    @PermitAll
    public List<HomePageResponse> getBestsellersForHomePage() {
        return homePageService.getBestsellersForHomePage();
    }

    @Operation(summary = "Get hot sales products!", description = "This method gets hot sales products for home page!")
    @GetMapping("/hot_sales")
    @PermitAll
    public List<HomePageResponse> getHotSalesForHomePage() {
        return homePageService.getHotSalesForHomePage();
    }

    @Operation(summary = "Get fashion products!", description = "This method gets fashion products for home page!")
    @GetMapping("/fashions")
    @PermitAll
    public List<HomePageResponse> getFashionProductsForHomePage() {
        return homePageService.getFashionProductsForHomePage();
    }

    @Operation(summary = "Get popular brands!", description = "This method gets products which brands are popular for home page!")
    @GetMapping("/brands")
    @PermitAll
    public List<HomePageResponse> getPopularBrandsForHomePage() {
        return homePageService.getPopularBrandsForHomePage();
    }
}
