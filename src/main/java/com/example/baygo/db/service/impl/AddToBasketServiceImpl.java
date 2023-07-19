package com.example.baygo.db.service.impl;

import com.example.baygo.db.config.jwt.JwtService;
import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Size;
import com.example.baygo.db.repository.BuyerRepository;
import com.example.baygo.db.repository.SizeRepository;
import com.example.baygo.db.service.AddBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddToBasketServiceImpl implements AddBasketService {
    private final JwtService jwtService;
    private final SizeRepository sizeRepository;
    private final BuyerRepository buyerRepository;
    @Override
    public SimpleResponse addToBasket(Long sizeId) {
        Size size = sizeRepository.findById(sizeId).orElseThrow();
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        buyer.setBasket(List.of(size));
        buyerRepository.save(buyer);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(
                String.format("Продукт добавлен %s в корзину",sizeId)).build();
    }

    @Override
    public List<ProductsInBasketResponse> getAllProductFromBasket() {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        buyer.getBasket().listIterator();


        return null;
    }


    @Override
    public SimpleResponse deleteFromBasket(Long sizeId) {
        return null;
    }
}
