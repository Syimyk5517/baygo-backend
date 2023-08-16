package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.ReviewByBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.product.ReviewGetByIdResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Review;
import com.example.baygo.db.model.SubProduct;
import com.example.baygo.repository.ReviewByBuyerRepository;
import com.example.baygo.repository.SubProductRepository;
import com.example.baygo.repository.custom.CustomReviewRepository;
import com.example.baygo.service.ReviewByBuyerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewByBuyerServiceImpl implements ReviewByBuyerService {
    private final ReviewByBuyerRepository reviewByBuyerRepository;
    private final JwtService jwtService;
    private final SubProductRepository subProductRepository;
    private final CustomReviewRepository customReviewRepository;

    @Override
    public SimpleResponse saveReview(ReviewByBuyerRequest request) {
        SubProduct subProduct = subProductRepository.findById(request.subProductId()).orElseThrow(() -> new NotFoundException("Под продукт с идентификатором: " + request.subProductId() + " не найден!"));
        Review review = new Review();
        review.setBuyer(jwtService.getAuthenticate().getBuyer());
        review.setSubProduct(subProduct);
        review.setGrade(request.grade());
        review.setImages(request.images());
        review.setText(request.text());
        review.setDateAndTime(LocalDateTime.now());
        reviewByBuyerRepository.save(review);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Отзыв успешно отправлен!").build();
    }

    @Override
    public ReviewGetByIdResponse getAllReviewByProduct(Long subProductId, boolean withImages) {
        return customReviewRepository.getAllReviewsOfProduct(subProductId,withImages);
    }
}
