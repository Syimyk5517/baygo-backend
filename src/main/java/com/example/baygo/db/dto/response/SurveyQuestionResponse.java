package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionResponse {
    private Long questionId;
    private String title;
    private List<OptionResponse> optionResponses;
}
