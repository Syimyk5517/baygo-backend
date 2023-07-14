package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.repository.SellerRepository;
import com.example.baygo.db.service.SellerService;
import com.example.baygo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    @Override
    public SimpleResponse sellerProfile(Long userId, SellerProfileRequest request) {
        log.error(String.format("Пользователь с этим идентификатором %s не найден", userId));
      User user = userRepository.findById(userId).orElseThrow(()->
       new NotFoundException(String.format("Пользователь с этим идентификатором %s не найден",userId)));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.getSeller().setAddress(request.getAddress());
        userRepository.save(user);

        log.info("Личная информация продавца успешно обновлена");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Личная информация продавца успешно обновлена").build();
    }

    @Override
    public SimpleResponse sellerStoreInfo(Long sellerId, SellerStoreInfoRequest request) {
        log.error(String.format("Продавец с этим идентификатором %s не найден", sellerId));
        Seller seller =   sellerRepository.findById(sellerId).orElseThrow(()->
               new NotFoundException(String.format("Продавец с этим идентификатором %s не найден",sellerId)));
        seller.setPhoto(request.getPhoto());
        seller.setNameOfStore(request.getNameOfStore());
        seller.getUser().setEmail(request.getStoreEmail());
        seller.setAddress(request.getStoreAddress());
        seller.setITN(request.getITN());
        seller.setStoreLogo(request.getStoreLogo());
        seller.setBIC(request.getBIC());
        seller.setAboutStore(request.getAboutStore());
        sellerRepository.save(seller);

        log.info("Информация магазина продавца успешно обновлена");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).
                message("Информация магазина продавца успешно обновлена.").build();
    }
}
