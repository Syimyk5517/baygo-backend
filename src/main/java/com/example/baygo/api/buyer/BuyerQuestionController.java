package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.request.QuestionOfBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.QuestionOfBuyerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer/questions")
@RequiredArgsConstructor
@Tag(name = "Question Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerQuestionController {
    private final QuestionOfBuyerService service;

    @Operation(summary = "Save Question",description = "This method saves questions which written by Buyer!")
    @PostMapping
    @PreAuthorize("hasAuthority('BUYER')")
    public SimpleResponse sendQuestions(@RequestBody @Valid QuestionOfBuyerRequest request){
       return service.saveQuestions(request);
    }
}
