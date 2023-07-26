package com.example.baygo.db.api;

import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.service.GetAllQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/get_all_questions_reviews")
@RequiredArgsConstructor
@Tag(name = "Get all question and reviews API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GetAllQuestionAndReviews {
    private final GetAllQuestionService service;
    @Operation(summary = "Get all questions", description = "This method will get all questions of buyer")
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping
    public SimpleResponse getAllQuestions(){
        return service.getAllQuestions();
    }
}
