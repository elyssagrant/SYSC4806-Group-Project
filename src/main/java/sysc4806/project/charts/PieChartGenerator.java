package sysc4806.project.charts;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import sysc4806.project.survey.responses.MultipleChoiceResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders a pie chart representation of the responses for
 * choice-style questions.
 */
public class PieChartGenerator extends ChartGenerator{

    private String title;
    private DefaultPieDataset<String> dataset;
    private final SurveyRepository surveyRepository;

    public PieChartGenerator(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
        title = "";
        dataset = new DefaultPieDataset<String>();
    }

    /**
     * Analyzes information from question entry in survey repo to
     * extract pie chart title and frequencies.
     */
    private void extractChartDetails(Integer surveyId, Integer questionId) {

        // Query survey repo for survey of interest
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        // Get question of interest from survey
        MultipleChoiceQuestion question = null;
        for (MultipleChoiceQuestion q : survey.getQuestions()) {
            if (q.getId() == questionId) {
                question = q;
                break;
            }
        }

        // Create dataset of responses
        if(question != null) {
            this.title = question.getQuestion();

            // Count instances of each response
            ArrayList<MultipleChoiceResponse> responses = question.getResponses();
            HashMap<String, Integer> responseCount = new HashMap<>();
            for (MultipleChoiceResponse r : responses) {
                responseCount.put(r.getResponse(), responseCount.getOrDefault(r.getResponse(), 0) + 1);
            }

            // Add responses and corresponding counts to dataset
            for(Map.Entry<String, Integer> entry : responseCount.entrySet()) {
                this.dataset.setValue(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Generates a pie chart representation for a particular
     * question's responses.
     *
     * @param surveyId The survey of interest's ID number
     * @param questionId The question of interest's ID number
     */
    @Override
    public byte[] generateChart(Integer surveyId, Integer questionId) {

        // Get dataset for chart
        extractChartDetails(surveyId, questionId);

        //Generate chart
        JFreeChart chart = ChartFactory.createPieChart(
                this.title,
                dataset,
                true,    // include legend
                true,           // generate tooltips
                false);         // no URLs

        //Convert to PNG
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            ChartUtils.writeChartAsPNG(output,
                    chart,
                    ChartGenerator.CHART_WIDTH,
                    ChartGenerator.CHART_HEIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output.toByteArray();
    }

}
