package com.example.baygo.repository;

import com.example.baygo.db.model.Survey;
import com.example.baygo.db.model.enums.SurveyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

        Survey getSurveyBySurveyType(SurveyType surveyType);

}
