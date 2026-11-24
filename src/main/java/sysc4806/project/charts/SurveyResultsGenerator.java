package sysc4806.project.charts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.*;

@Service
public class SurveyResultsGenerator {

    private final SurveyRepository surveyRepository;

    public SurveyResultsGenerator(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public List<QuestionResult> generateResults(Integer surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(
                () -> new IllegalArgumentException("Can not find survey with id: " + surveyId));

        List<QuestionResult> results = new ArrayList<>();

        return results;
    }
}