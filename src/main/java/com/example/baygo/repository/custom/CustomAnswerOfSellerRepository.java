package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomAnswerOfSellerRepository {
    PaginationResponse<BuyerQuestionResponse> getAllQuestions(String keyWord, int page, int pageSize, Long sellerId);

    List<QuestionForSellerLandingResponse> getAllQuestionsForSeller(Long sellerId);
}
