package com.example.baygo.service;

import com.example.baygo.db.dto.request.PassSurveyRequest;
import com.example.baygo.db.dto.request.SurveyRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.SurveyResponse;
import com.example.baygo.db.model.enums.SurveyType;

public interface SurveyService {
    SimpleResponse createSurvey(SurveyRequest request);

    SurveyResponse getSurvey(SurveyType surveyType);

    SimpleResponse passSurvey(SurveyType surveyType, PassSurveyRequest passSurveyRequest);
}
