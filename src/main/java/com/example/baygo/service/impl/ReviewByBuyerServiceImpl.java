package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.ReviewByBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Product;
import com.example.baygo.db.model.Review;
import com.example.baygo.repository.ProductRepository;
import com.example.baygo.repository.ReviewByBuyerRepository;
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
    private final ProductRepository productRepository;
    private final ReviewByBuyerRepository reviewByBuyerRepository;
    private final JwtService jwtService;
    @Override
    public SimpleResponse saveReview(ReviewByBuyerRequest request) {
        Product product = productRepository.findById(request.productId()).orElseThrow(() -> new NotFoundException("Продукт с идентификатором: " + request.productId() + " не найден!"));
        Review review = new Review();
        review.setBuyer(jwtService.getAuthenticate().getBuyer());
        review.setProduct(product);
        review.setGrade(request.grade());
        review.setImages(request.images());
        review.setText(request.text());
        review.setDateAndTime(LocalDateTime.now());
        reviewByBuyerRepository.save(review);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Отзыв успешно отправлен!").build();
    }
}
