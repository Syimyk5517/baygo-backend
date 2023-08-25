package com.example.baygo.service.impl;
import com.example.baygo.db.dto.request.*;
import com.example.baygo.db.dto.response.OptionResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.SurveyQuestionResponse;
import com.example.baygo.db.dto.response.SurveyResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Answer;
import com.example.baygo.db.model.Option;
import com.example.baygo.db.model.Question;
import com.example.baygo.db.model.Survey;
import com.example.baygo.db.model.enums.SurveyType;
import com.example.baygo.repository.AnswerRepository;
import com.example.baygo.repository.OptionRepository;
import com.example.baygo.repository.SurveyRepository;
import com.example.baygo.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final OptionRepository optionRepository;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;

    @Override
    public SimpleResponse createSurvey(SurveyRequest request) {

        Survey survey = new Survey();
        survey.setSurveyType(request.getSurveyType());
        survey.setTitle(request.getTitle());

        for (QuestionRequest questionRequest : request.getQuestionRequests()) {
            Question question = new Question();
            question.setTitle(questionRequest.title());

            for (OptionRequest optionRequest : questionRequest.optionRequests()) {
                Option option = new Option();
                option.setOptionOrder(optionRequest.getOptionOrder());
                option.setTitle(optionRequest.getTitle());
                option.setQuestion(question);
                optionRepository.save(option);
            }
            survey.addQuestion(question);
            question.setSurvey(survey);
        }

        surveyRepository.save(survey);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Survey successfully created").build();
    }

    @Override
    public SurveyResponse getSurvey(SurveyType surveyType) {

        List<SurveyQuestionResponse> surveyQuestionResponses = new ArrayList<>();

        List<Survey> surveysBySurveyType = surveyRepository.getSurveyBySurveyType(surveyType);

        Survey survey = surveysBySurveyType.get(surveysBySurveyType.size()-1);

        for (Question question : survey.getQuestions()) {
            SurveyQuestionResponse surveyQuestionResponse = new SurveyQuestionResponse();
            surveyQuestionResponse.setTitle(question.getTitle());
            surveyQuestionResponse.setQuestionId(question.getId());

            List<OptionResponse> optionResponses = new ArrayList<>();

            for (Option option : question.getOptions()) {
                OptionResponse optionResponse = new OptionResponse(); // Create a new OptionResponse object for each option
                optionResponse.setOptionId(option.getId());
                optionResponse.setOptionTitle(option.getTitle());
                optionResponses.add(optionResponse);
            }

            surveyQuestionResponse.setOptionResponses(optionResponses);
            surveyQuestionResponses.add(surveyQuestionResponse);
        }

        return SurveyResponse.builder()
                .questionResponses(surveyQuestionResponses)
                .surveyType(surveyType).build();
    }


    @Override
    public SimpleResponse passSurvey(SurveyType surveyType, PassSurveyRequest passSurveyRequest) {

        Answer answer = new Answer();
        List<Option> options = new ArrayList<>();

        for (Long optionIde : passSurveyRequest.optionIdes()) {
            options.add(optionRepository.findById(optionIde)
                    .orElseThrow(() -> new NotFoundException("Option with id " + optionIde + " not found")));
        }

        answer.setFullName(passSurveyRequest.fullName());
        answer.setPhoneNumber(passSurveyRequest.phoneNumber());
        answer.setRating(passSurveyRequest.rating());
        answer.setSuggestionOrComment(passSurveyRequest.suggestionOrComments());
        answer.setOptions(options);

        answerRepository.save(answer);

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Your answer saved successful").build();
    }


}
