package com.example.baygo.service;

import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.request.QuestionOfSellerUpdateRequest;
import com.example.baygo.db.dto.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerOfSellerService {
    SimpleResponse addAnswer(AnswerOfSellerRequest request);

    PaginationReviewAndQuestionResponse<BuyerQuestionResponse> getAllQuestions(boolean isAnswered, String keyWord, int page, int pageSize);

    List<QuestionForSellerLandingResponse> getAllQuestionsForLandingOfSeller();
    SimpleResponse questionUpdate(QuestionOfSellerUpdateRequest request);
}
