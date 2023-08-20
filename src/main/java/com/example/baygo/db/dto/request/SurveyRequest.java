package com.example.baygo.db.dto.request;

import com.example.baygo.db.model.enums.SurveyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class SurveyRequest {
    private String title;
    private List<QuestionRequest>questionRequests;
    private SurveyType surveyType;
}
