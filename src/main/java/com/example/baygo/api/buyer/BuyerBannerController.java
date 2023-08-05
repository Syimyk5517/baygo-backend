package com.example.baygo.api.buyer;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buyer/banners")
@RequiredArgsConstructor
@Tag(name = "Banner Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerBannerController {
}
