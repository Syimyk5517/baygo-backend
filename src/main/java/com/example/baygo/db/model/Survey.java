package com.example.baygo.db.model;

import com.example.baygo.db.model.enums.SurveyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "surveys")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "survey_gen")
    @SequenceGenerator(name = "survey_gen", sequenceName = "survey_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private SurveyType surveyType;
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

    public void addQuestion(Question question){
        if (this.questions == null){
            List<Question>questions1 = new ArrayList<>();
            questions1.add(question);
            questions = questions1;
        }else questions.add(question);
    }
}
