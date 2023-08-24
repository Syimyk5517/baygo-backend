package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.FavoriteResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.SubProduct;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.repository.SubProductRepository;
import com.example.baygo.service.FavoriteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final SizeRepository sizeRepository;
    private final JwtService jwtService;
    private final SubProductRepository subProductRepository;

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

    @Transactional
    @Override
    public SimpleResponse toggleFavorite(Long subProductId) {
        Buyer buyer1 = jwtService.getAuthenticate().getBuyer();
        SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(
                () -> new NotFoundException(String.format("Продукт %s не найден!", subProductId)));
        if (buyer1.getFavorites().contains(subProduct)) {
            buyer1.getFavorites().remove(subProduct);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Продукт успешно удален из избранного!")
                    .build();
        } else {
            buyer1.getFavorites().add(subProduct);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Продукт успешно добавлен в избранное!")
                    .build();
        }
    }

    @Transactional
    @Override
    public SimpleResponse deleteFavor() {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        buyer.getFavorites().clear();
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Продукты успешно удалены из избранного!")
                .build();
    }
}

