package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.product.ProductGetByIdResponse;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.User;
import com.example.baygo.repository.custom.CustomProductRepository;
import com.example.baygo.service.SubProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {
    private final JwtService jwtService;
    private final CustomProductRepository customProductRepository;

    @Override
    public ProductGetByIdResponse getById(Long subProductId) {


        User user = jwtService.getAuthenticate();
        Buyer buyer = user.getBuyer();
        return customProductRepository.getById(buyer.getId(), subProductId);
    }
}
