package com.example.baygo.api;
import com.example.baygo.db.dto.request.PassSurveyRequest;
import com.example.baygo.db.dto.request.SurveyRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.SurveyResponse;
import com.example.baygo.db.model.enums.SurveyType;
import com.example.baygo.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
@Tag(name = "Survey API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SurveyController {
    private final SurveyService surveyService;
    @Operation(summary = "Create survey ", description = "This method create survey.")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse createSurvey(@RequestBody SurveyRequest surveyRequest){
        return surveyService.createSurvey(surveyRequest);
    }

    @Operation(summary = "Get survey from buyers", description = "This method get survey from buyers.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('SELLER', 'BUYER', 'ADMIN')")
    public SurveyResponse getSurvey(@RequestParam SurveyType surveyType){
        return surveyService.getSurvey(surveyType);
    }
    @Operation(summary = "Pass survey", description = "This method pass the Survey.")
    @PostMapping("/pass")
    @PreAuthorize("hasAnyAuthority('SELLER', 'BUYER', 'ADMIN')")
    public SimpleResponse passSurvey(@RequestParam SurveyType surveyType,
                                     @RequestBody PassSurveyRequest passSurveyRequest){
        return surveyService.passSurvey(surveyType, passSurveyRequest);
    }
}