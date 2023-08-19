package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.request.QuestionOfSellerUpdateRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.BuyerQuestion;
import com.example.baygo.repository.QuestionOfBuyerRepository;
import com.example.baygo.repository.custom.CustomAnswerOfSellerRepository;
import com.example.baygo.service.AnswerOfSellerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerOfSellerServiceImpl implements AnswerOfSellerService {
    private final QuestionOfBuyerRepository buyerQuestionRepository;
    private final CustomAnswerOfSellerRepository customAnswerOfSellerRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse addAnswer(AnswerOfSellerRequest request) {
        BuyerQuestion question = buyerQuestionRepository.findById(request.questionId()).orElseThrow(() -> new NotFoundException("Вопрос с идентификатором: " + request.questionId() + " не найден!"));
        question.setAnswer(request.answer());
        question.setReplyDate(LocalDateTime.now());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Ответ успешно сохранено!").build();
    }

    @Override
    public PaginationReviewAndQuestionResponse<BuyerQuestionResponse> getAllQuestions(boolean isAnswered, boolean ascending, String keyWord, int page, int pageSize) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        Pageable pageable = PageRequest.of(page - 1, pageSize, ascending ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending());

        Page<BuyerQuestionResponse> allQuestions = buyerQuestionRepository.getAllQuestions(sellerId, isAnswered, keyWord, pageable);
        int countOfUnanswered = buyerQuestionRepository.countOfUnansweredBySellerId(sellerId);
        int countOfArchive = buyerQuestionRepository.countOfAnsweredBySellerId(sellerId);

        PaginationReviewAndQuestionResponse<BuyerQuestionResponse> response = PaginationReviewAndQuestionResponse.<BuyerQuestionResponse>builder()
                .countOfUnanswered(countOfUnanswered)
                .countOfArchive(countOfArchive)
                .elements(allQuestions.getContent())
                .currentPage(allQuestions.getNumber() + 1)
                .totalPages(allQuestions.getTotalPages())
                .build();

        return response;
    }

    @Override
    public List<QuestionForSellerLandingResponse> getAllQuestionsForLandingOfSeller() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customAnswerOfSellerRepository.getAllQuestionsForSeller(sellerId);
    }

    @Override
    public SimpleResponse questionUpdate(QuestionOfSellerUpdateRequest request) {
        BuyerQuestion question = buyerQuestionRepository.findById(request.id()).orElseThrow(() -> new NotFoundException(String.format("Вопрос с идентификатором: " + request.id() + " не найден!")));
        question.setAnswer(request.answer());
        question.setCreatedAt(LocalDateTime.now());
        buyerQuestionRepository.save(question);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully update!!!")
                .build();
    }
}
