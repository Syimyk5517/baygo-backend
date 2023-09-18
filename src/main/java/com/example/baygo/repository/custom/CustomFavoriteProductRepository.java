package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.HomePageResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomFavoriteProductRepository {
    List<HomePageResponse> findAllFavoriteItems();
}
