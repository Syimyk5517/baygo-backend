package com.example.baygo.service;

import com.example.baygo.db.dto.response.HomePageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface HomePageService {
    public List<HomePageResponse> findAllFavoriteItems();
}
