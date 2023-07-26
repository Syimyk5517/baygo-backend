package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.repository.BuyerQuestionRepository;
import com.example.baygo.db.service.GetAllQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class GetAllQuestionServiceImpl implements GetAllQuestionService {
    private final BuyerQuestionRepository repository;
    @Override
    public SimpleResponse getAllQuestions() {
         repository.findAll();
         return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Вопросы покупателья ").build();
    }
}
