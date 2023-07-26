package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface GetAllQuestionService {
    SimpleResponse getAllQuestions();
}
