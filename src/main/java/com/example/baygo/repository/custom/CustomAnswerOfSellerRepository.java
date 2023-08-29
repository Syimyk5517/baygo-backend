package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomAnswerOfSellerRepository {

    List<QuestionForSellerLandingResponse> getAllQuestionsForSeller(Long sellerId);
}
