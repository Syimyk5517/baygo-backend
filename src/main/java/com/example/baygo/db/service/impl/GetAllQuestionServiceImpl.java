package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.repository.custom.CustomQuestionRepository;
import com.example.baygo.db.service.GetAllQuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class GetAllQuestionServiceImpl implements GetAllQuestionService {
    private final CustomQuestionRepository customQuestionRepository;

    @Override
    public List<BuyerQuestionResponse> getAllQuestions() {
        return customQuestionRepository.getAllQuestions();
    }
}

