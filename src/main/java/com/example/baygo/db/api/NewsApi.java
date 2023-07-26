package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.NewsResponse;
import com.example.baygo.db.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sellers/news")
@RequiredArgsConstructor
@Tag(name = "News api")
@CrossOrigin(origins = "*",maxAge = 3600)
public class NewsApi {
    private final NewsService newsService;

    @Operation(summary = "Get all news", description = "This method get all news")
    @GetMapping("/get_news")
    @PreAuthorize("hasAuthority('SELLER')")
    List<NewsResponse> getNews() {
        return newsService.getAllNews();
    }
}
