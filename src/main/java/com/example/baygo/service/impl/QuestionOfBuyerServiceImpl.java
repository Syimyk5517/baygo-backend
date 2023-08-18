package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.QuestionOfBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.BuyerQuestion;
import com.example.baygo.db.model.SubProduct;
import com.example.baygo.repository.QuestionOfBuyerRepository;
import com.example.baygo.repository.SubProductRepository;
import com.example.baygo.service.QuestionOfBuyerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionOfBuyerServiceImpl implements QuestionOfBuyerService {
    private final JwtService jwtService;
    private final QuestionOfBuyerRepository repository;
    private final SubProductRepository subProductRepository;

    @Override
    public SimpleResponse saveQuestions(QuestionOfBuyerRequest request) {
        SubProduct subProduct = subProductRepository.findById(request.subProductId()).orElseThrow(() -> new NotFoundException("Под продукт с идентификатором: " + request.subProductId() + " не найден!"));
        BuyerQuestion question = new BuyerQuestion();
        question.setQuestion(request.text());
        question.setBuyer(jwtService.getAuthenticate().getBuyer());
        question.setSubProduct(subProduct);
        question.setCreatedAt(LocalDateTime.now());
        repository.save(question);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Вопрос успешно отправлен!").build();
    }
}
