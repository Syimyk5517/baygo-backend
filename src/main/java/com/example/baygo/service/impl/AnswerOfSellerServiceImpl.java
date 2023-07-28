package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.BuyerQuestion;
import com.example.baygo.db.model.Seller;
import com.example.baygo.repository.BuyerQuestionRepository;
import com.example.baygo.repository.custom.CustomAnswerOfSellerRepository;
import com.example.baygo.service.AnswerOfSellerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerOfSellerServiceImpl implements AnswerOfSellerService {
    private final BuyerQuestionRepository buyerQuestionRepository;
    private final CustomAnswerOfSellerRepository customAnswerOfSellerRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse addAnswer(AnswerOfSellerRequest request) {
        BuyerQuestion question = buyerQuestionRepository.findById(request.questionId()).orElseThrow(() -> new NotFoundException("Вопрос с идентификатором: " + request.questionId() + " не найден!"));
        question.setAnswer(request.answer());
        question.setReplyDate(LocalDateTime.now());
        buyerQuestionRepository.save(question);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Ответ успешно сохранено!").build();
    }

    @Override
    public PaginationResponse<BuyerQuestionResponse> getAllQuestions(String keyWord, int page, int pageSize) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        return customAnswerOfSellerRepository.getAllQuestions(keyWord,page,pageSize, seller.getId());
    }

    @Override
    public List<QuestionForSellerLandingResponse> getAllQuestionsForLandingOfSeller() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customAnswerOfSellerRepository.getAllQuestionsForSeller(sellerId);
    }
}
