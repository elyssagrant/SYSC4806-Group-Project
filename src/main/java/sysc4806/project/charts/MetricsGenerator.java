package sysc4806.project.charts;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.MultipleChoiceQuestion;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.Question;

import java.util.ArrayList;

/**
 * MetricsGenerator organizes the metics of an entire survey
 * by generating the chart that corresponds to each question.
 */
public class MetricsGenerator {
    private final SurveyRepository surveyRepository;

    public MetricsGenerator(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Generate list of charts corresponding to a survey's responses.
     * @param surveyId The survey of interest's ID
     * @return list of charts (each represented as byte[])
     */
    public ArrayList<byte[]> generateMetrics(int surveyId) {
        // Query survey repo for survey of interest
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        // Initialize local variables
        ArrayList<byte[]> metrics = new ArrayList<>();
        PieChartGenerator pieChartGenerator = new PieChartGenerator();
        HistogramGenerator histogramGenerator = new HistogramGenerator();

        // Generate chart for each question
        for(Question q : survey.getQuestions()) {
            if(q instanceof MultipleChoiceQuestion) {
                metrics.add(pieChartGenerator.generateChart(q));
            }else if(q instanceof NumberRangeQuestion) {
                metrics.add(histogramGenerator.generateChart(q));
            }
        }

        return metrics;
    }
}