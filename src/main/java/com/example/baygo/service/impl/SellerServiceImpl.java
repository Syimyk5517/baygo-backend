package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SellerProfileResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.service.SellerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {
    private final JwtService jwtService;

    @Override
    public SellerProfileResponse getSellerProfile() {
        Seller seller = jwtService.getAuthenticate().getSeller();

        return SellerProfileResponse
                .builder()
                .firstName(seller.getFirstName())
                .lastName(seller.getLastName())
                .email(seller.getUser().getEmail())
                .phoneNumber(seller.getUser().getPhoneNumber())
                .address(seller.getAddress())
                .nameOfStore(seller.getNameOfStore())
                .storeAddress(seller.getAddressOfStore())
                .ITN(seller.getITN())
                .storeLogo(seller.getStoreLogo())
                .checkingCheck(seller.getCheckingCheck())
                .BIC(seller.getBIC())
                .aboutStore(seller.getAboutStore())
                .build();
    }

    @Transactional
    @Override
    public SimpleResponse updateSellerProfile(SellerProfileRequest request) {
        User user = jwtService.getAuthenticate();
        user.getSeller().setFirstName(request.getFirstName());
        user.getSeller().setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.getSeller().setAddress(request.getAddress());


        log.info("Личная информация продавца успешно обновлена");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Личная информация продавца успешно обновлена").build();
    }

    @Transactional
    @Override
    public SimpleResponse updateSellerStoreInfo(SellerStoreInfoRequest request) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        seller.setNameOfStore(request.getNameOfStore());
        seller.setAddressOfStore(request.getStoreAddress());
        seller.setITN(request.getITN());
        seller.setStoreLogo(request.getStoreLogo());
        seller.setBIC(request.getBIC());
        seller.setAboutStore(request.getAboutStore());

        log.info("Информация о магазине продавца успешно обновлена");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).
                message("Информация о магазине продавца успешно обновлена.").build();
    }

    @Override
    @Transactional
    public SimpleResponse updateLogoOfStore(String newLogo) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        if (newLogo == null || newLogo.isEmpty()) {
            log.error("Logo of store is null or empty");
            throw new BadRequestException("Логотип магазина не может быть пустым");
        }
        seller.setStoreLogo(newLogo);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Логотип магазина успешно обновлен")
                .build();
    }
}