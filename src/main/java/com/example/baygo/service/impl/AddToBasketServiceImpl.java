package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Size;
import com.example.baygo.repository.BuyerRepository;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.repository.custom.impl.CustomAddToBasketRepositoryImpl;
import com.example.baygo.service.AddToBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AddToBasketServiceImpl implements AddToBasketService {
    private final SizeRepository sizeRepository;
    private final CustomAddToBasketRepositoryImpl customAddToBasketRepository;
    private final BuyerRepository buyerRepository;
    private final JwtService jwtService;
    @Override
    public SimpleResponse addToBasket(Long sizeId) {
        Size size = sizeRepository.findById(sizeId).orElseThrow( ()-> new NotFoundException(sizeId + "Такой размер не найден"));
        Buyer buyer= jwtService.getAuthenticate().getBuyer();
        buyer.addBasket(size);
        buyerRepository.save(buyer);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(
                "Продукт добавлен в корзину.").build();
    }

    @Override
    public List<ProductsInBasketResponse> getAllProductsFromBasket() {
        return customAddToBasketRepository.getAllProductFromBasket();
    }

    @Override
    public SimpleResponse deleteFromBasket(Long sizeId) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        Size size = sizeRepository.findById(sizeId).orElseThrow(() ->
                new NotFoundException("Такой продукт не найден в корзине."));
        buyer.getBasket().remove(size);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Продукт из корзины удалена.").build();
    }
}
