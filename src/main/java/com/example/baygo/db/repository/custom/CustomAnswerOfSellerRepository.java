package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.db.dto.response.PaginationResponse;

import java.util.List;

public interface CustomAnswerOfSellerRepository {
    PaginationResponse<BuyerQuestionResponse> getAllQuestions(String keyWord, int page, int pageSize, Long sellerId);

    List<QuestionForSellerLandingResponse> getAllQuestionsForSeller(Long sellerId);
}
