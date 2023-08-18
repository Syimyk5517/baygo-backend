package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.PaginationReviewAndQuestionResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.AnswerOfSellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/answer")
@RequiredArgsConstructor
@Tag(name = "Seller answer question")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerAnswerQuestionController {
    private final AnswerOfSellerService answerOfSellerService;

    @Operation(summary = "Save answer!", description = "This method saves answer for question!")
    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse saveAnswer(@Valid @RequestBody AnswerOfSellerRequest request) {
        return answerOfSellerService.addAnswer(request);
    }

    @Operation(summary = "Get all questions!", description = "This method gets all questions. IsAnswered: false - to get unanswered questions, true - to get archive of questions.")
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/questions")
    public PaginationReviewAndQuestionResponse<BuyerQuestionResponse> getQuestions(
            @RequestParam(defaultValue = "false") boolean isAnswered,
            @RequestParam(defaultValue = "false") boolean ASC,
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int pageSize) {
        return answerOfSellerService.getAllQuestions(isAnswered, ASC, keyWord, page, pageSize);
    }
}