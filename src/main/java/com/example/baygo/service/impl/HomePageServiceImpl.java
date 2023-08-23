package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.HomePageResponse;
import com.example.baygo.repository.custom.CustomFavoriteProductRepository;
import com.example.baygo.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class HomePageServiceImpl implements HomePageService {
    private final CustomFavoriteProductRepository customFavoriteProductRepository;
    @Override
    public List<HomePageResponse> findAllFavoriteItems() {
        return customFavoriteProductRepository.findAllFavoriteItems();
    }
}
