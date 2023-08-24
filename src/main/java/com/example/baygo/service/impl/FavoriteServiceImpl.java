package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.buyer.FavoriteResponse;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final SizeRepository sizeRepository;
    private final JwtService jwtService;

    @Override
    public PaginationResponse<FavoriteResponse> getAllFavorProduct(int page, int size, String search) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<FavoriteResponse> favoriteResponses = sizeRepository.getBuyerFavorites(buyer.getId(), search, pageable);

        return new PaginationResponse<>(
                favoriteResponses.getContent(),
                favoriteResponses.getNumber() + 1,
                favoriteResponses.getTotalPages());
    }
}

