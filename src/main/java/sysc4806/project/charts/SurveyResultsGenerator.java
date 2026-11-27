package sysc4806.project.charts;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.*;
import sysc4806.project.survey.responses.TextResponse;

/**
 * Takes a survey and returns a list of QuestionResult objects which contain data
 * about the question along with the results of that question.
 */
@Service
public class SurveyResultsGenerator {

    private final SurveyRepository surveyRepository;

    public SurveyResultsGenerator(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Takes a survey via surveyId. It then runs a for loop to generate a chart for each MCQ and NRQ, and a
     * list of answers for each text question. It adds the charts/list of answers to a QuestionResult object along
     * with the question id (Integer), question type (type), question text (String), result chart (chart), and
     * a list of text responses for text questions (ArrayList<String>).
     *
     * @param surveyId id of the desired survey to generate results for
     * @return list of QuestionResult objects which contain the results and additional data for each question
     */
    public List<QuestionResult> generateResults(Integer surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(
                () -> new IllegalArgumentException("Can not find survey with id: " + surveyId));

        List<QuestionResult> results = new ArrayList<>();

        for (Question q : survey.getQuestions()) {
            // number range questions
            if (q instanceof NumberRangeQuestion nrq) {
                byte[] chart = new HistogramGenerator(surveyRepository).generateChart(surveyId, nrq.getId());
                results.add(new QuestionResult(nrq.getId(), "NumberRange", nrq.getQuestionText(), Base64.getEncoder().encodeToString(chart), null));

                // multiple choice questions
            } else if (q instanceof MultipleChoiceQuestion mcq) {
                byte[] chart = new PieChartGenerator(surveyRepository).generateChart(surveyId, mcq.getId());
                results.add(new QuestionResult(mcq.getId(), "MultipleChoice", mcq.getQuestionText(), Base64.getEncoder().encodeToString(chart), null));

                // test questions
            } else if (q instanceof TextQuestion txtq) {
                List<String> answers = new ArrayList<>();
                for (TextResponse r : txtq.getResponses()) {
                    answers.add(r.getResponse());
                }

                // collect the results in the results list
                results.add(new QuestionResult(txtq.getId(), "Text", txtq.getQuestionText(), null, answers));
            }
        }
        return results;
    }
}