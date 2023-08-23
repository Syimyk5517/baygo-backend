package com.example.baygo.api;

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
@RequestMapping("/api/home/page")
@RequiredArgsConstructor
@Tag(name = "Home Page API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomePageController {
    private final HomePageService homePageService;
    @Operation(summary = "Find all favorite products",description = "This method find all favorite products ")
    @GetMapping("/favorite_products")
    @PermitAll
    public List<HomePageResponse> findAllFavoriteItems(){
        return homePageService.findAllFavoriteItems();
    }
}
