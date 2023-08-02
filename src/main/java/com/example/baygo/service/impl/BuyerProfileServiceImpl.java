package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.BuyerProfileImageRequest;
import com.example.baygo.db.dto.request.BuyerProfileRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Order;
import com.example.baygo.db.model.User;
import com.example.baygo.repository.OrderRepository;
import com.example.baygo.service.BuyerProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BuyerProfileServiceImpl implements BuyerProfileService {
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final OrderRepository orderRepository;
    private final JdbcTemplate jdbcTemplate;
    @Transactional
    @Override
    public SimpleResponse updateProfile(BuyerProfileRequest request) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        buyer.setFullName(request.fullName());
        buyer.setDateOfBirth(request.dateOfBirth());
        buyer.setGender(request.gender());
        buyer.setAddress(request.region());
        buyer.getUser().setEmail(request.email());
        buyer.getUser().setPassword(encoder.encode(request.password()));
        buyer.getUser().setPhoneNumber(request.phoneNumber());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Ваш профиль успешно изменен!").build();
    }
    @Transactional
    @Override
    public SimpleResponse updateProfileImage(BuyerProfileImageRequest request) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        buyer.setPhoto(request.imgUrl());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Ваше изображение успешно обновлено").build();
    }
    @Override
    public SimpleResponse deleteProfile() {
        User user = jwtService.getAuthenticate();
        Buyer buyer = user.getBuyer();
        for (Order order : buyer.getOrders()) {
            order.setBuyer(null);
            orderRepository.save(order);
        }
        String appeals = "delete from appeals a where a.buyer_id = ?";
        String buyers_baskets = "delete from buyers_baskets b where b.buyer_id = ?";
        String buyers_favorites = "delete from buyers_favorites b where b.buyer_id = ?";
        String buyers_last_views = "delete from buyers_last_views b where b.buyer_id = ?";
        String review_images = "delete from review_images r where r.review_id in (select r.id from reviews r where r.buyer_id = ?)";
        String reviews = "delete from reviews r where r.buyer_id = ?";
        String buyersQuery = "delete from buyers b where b.id = ?";
        jdbcTemplate.update(appeals, buyer.getId());
        jdbcTemplate.update(buyers_baskets, buyer.getId());
        jdbcTemplate.update(buyers_favorites, buyer.getId());
        jdbcTemplate.update(buyers_last_views, buyer.getId());
        jdbcTemplate.update(review_images, buyer.getId());
        jdbcTemplate.update(reviews, buyer.getId());
        jdbcTemplate.update(buyersQuery, buyer.getId());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ваш аккаунт успешно удален!!!")
                .build();
    }
}
