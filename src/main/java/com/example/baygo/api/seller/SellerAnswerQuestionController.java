package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.request.QuestionOfSellerUpdateRequest;
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
@PreAuthorize("hasAuthority('SELLER')")
public class SellerAnswerQuestionController {
    private final AnswerOfSellerService answerOfSellerService;

    @Operation(summary = "Save answer!", description = "This method saves answer for question!")
    @PostMapping
    public SimpleResponse saveAnswer(@Valid @RequestBody AnswerOfSellerRequest request) {
        return answerOfSellerService.addAnswer(request);
    }

    @Operation(summary = "Get all questions!", description = "This method gets all questions. IsAnswered: false - to get unanswered questions, true - to get archive of questions.")
    @GetMapping("/questions")
    public PaginationReviewAndQuestionResponse<BuyerQuestionResponse> getQuestions(
            @RequestParam(defaultValue = "false") boolean isAnswered,
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int pageSize) {
        return answerOfSellerService.getAllQuestions(isAnswered, keyWord, page, pageSize);
    }

    @PutMapping("/update")
    @Operation(summary = "Update seller's question",
            description = "Allows sellers to modify details of a question from a buyer. " +
                    "Text, status, and answer can be changed. Updated question is saved.")
    public SimpleResponse questionUpdate(@RequestBody QuestionOfSellerUpdateRequest request) {
        return answerOfSellerService.questionUpdate(request);
    }
}