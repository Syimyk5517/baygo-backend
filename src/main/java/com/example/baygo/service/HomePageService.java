package com.example.baygo.service;

import com.example.baygo.db.dto.response.HomePageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomePageService {
     List<HomePageResponse> getBestsellersForHomePage();
     List<HomePageResponse> getHotSalesForHomePage();
     List<HomePageResponse> getFashionProductsForHomePage();
     List<HomePageResponse> getPopularBrandsForHomePage();

     List<HomePageResponse> findAllFavoriteItems();
}
