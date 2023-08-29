package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.enums.SurveyType;
import lombok.Builder;

import java.util.List;

@Builder
public record SurveyResponse (
        SurveyType surveyType,
        List<SurveyQuestionResponse> questionResponses
)
{
}
