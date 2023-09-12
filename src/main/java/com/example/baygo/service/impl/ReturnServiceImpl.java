package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.seller.SellerReturnGetByIdResponse;
import com.example.baygo.db.dto.response.seller.SellerReturnResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Seller;
import com.example.baygo.repository.ReturnRepository;
import com.example.baygo.service.SellerReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements SellerReturnService {
    private final ReturnRepository returnRepository;
    private final JwtService jwtService;

    @Override
    public List<SellerReturnResponse> getAllReturns() {
        Seller seller = jwtService.getAuthenticate().getSeller();
        return returnRepository.getAllSellerReturns(seller.getId());
    }

    @Override
    public SellerReturnGetByIdResponse getById(Long returnId) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        SellerReturnGetByIdResponse returnById = returnRepository.getReturnById(seller.getId(), returnId)
                .orElseThrow(() -> new NotFoundException(String.format("Возврат с номером %s не найден", returnId)));

        List<String> returnImageBySeller = returnRepository.getReturnImageBySeller(seller.getId(), returnId);
        returnById.setImages(returnImageBySeller);

        return returnById;
    }
}
