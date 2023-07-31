package com.example.baygo.service;

import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerOfSellerService {
    SimpleResponse addAnswer(AnswerOfSellerRequest request);

    PaginationResponse<BuyerQuestionResponse> getAllQuestions(String keyWord, int page, int pageSize);

    List<QuestionForSellerLandingResponse> getAllQuestionsForLandingOfSeller();
}
