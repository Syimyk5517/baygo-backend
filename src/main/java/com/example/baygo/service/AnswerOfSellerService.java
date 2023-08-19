package com.example.baygo.service;

import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.request.QuestionOfSellerUpdateRequest;
import com.example.baygo.db.dto.response.*;

import java.util.List;

public interface AnswerOfSellerService {
    SimpleResponse addAnswer(AnswerOfSellerRequest request);

    PaginationReviewAndQuestionResponse<BuyerQuestionResponse> getAllQuestions(boolean isAnswered, boolean ascending, String keyWord, int page, int pageSize);

    List<QuestionForSellerLandingResponse> getAllQuestionsForLandingOfSeller();
    SimpleResponse questionUpdate(QuestionOfSellerUpdateRequest request);
}
