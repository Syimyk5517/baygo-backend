package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.PaginationReviewAndQuestionResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomAnswerOfSellerRepository {
    PaginationReviewAndQuestionResponse<BuyerQuestionResponse> getAllQuestions(boolean isAnswered, String keyWord, int page, int pageSize, Long sellerId);

    List<QuestionForSellerLandingResponse> getAllQuestionsForSeller(Long sellerId);
}
