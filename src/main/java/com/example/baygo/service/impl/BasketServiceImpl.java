package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Size;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.repository.custom.impl.CustomAddToBasketRepositoryImpl;
import com.example.baygo.service.BasketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final SizeRepository sizeRepository;
    private final CustomAddToBasketRepositoryImpl customAddToBasketRepository;
    private final JwtService jwtService;
    @Override
    @Transactional
    public SimpleResponse addToBasketOrDelete(Long sizeId, boolean delete) {
        Size size = sizeRepository.findById(sizeId).orElseThrow(() -> new NotFoundException(sizeId + "Такой размер не найден"));
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        boolean isInBasket = buyer.getBasket().contains(size);

        if (isInBasket) {
            buyer.getBasket().remove(size);
        } else {
            buyer.addToBasket(size);
        }

        String message = isInBasket ? "Продукт удален из корзины." : "Продукт добавлен в корзину.";

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(message)
                .build();
    }

    @Override
    public List<ProductsInBasketResponse> getAllProductsFromBasket() {
        return customAddToBasketRepository.getAllProductFromBasket();
    }

    @Override
    @Transactional
    public SimpleResponse deleteAllFromBasket() {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        buyer.getBasket().clear();
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Продукты удалены из корзины.").build();
    }
}
