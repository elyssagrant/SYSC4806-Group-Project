package sysc4806.project.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.responses.NumberRangeResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Renders a histogram representation of responses for number
 * in range style questions
 */
public class HistogramGenerator extends ChartGenerator {

    private static final int NUM_CATEGORIES = 3;
    private String title;
    private final DefaultCategoryDataset dataset;
    private final SurveyRepository surveyRepository;

    public HistogramGenerator(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
        title = "";
        dataset = new DefaultCategoryDataset();
    }

    /**
     * Creates sub-ranges for histogram given upper and lower bounds (inclusive).
     * In instances where the total range cannot be equally distributed,
     * remainder values will be added to beginning sub-range.
     * @param min The lower bound of the total range
     * @param max The upper bound of the total range
     * @return a list of sub-ranges as int arrays in the format {lower sub-bound, upper sub-bound}
     */
    public ArrayList<int[]> getSubRanges(int min, int max) {
        ArrayList<int[]> subRanges = new ArrayList<int[]>();
        int subRangeSize = (max - min + 1) / NUM_CATEGORIES;
        int remainder = (max - min + 1) % NUM_CATEGORIES;

        int start = min;
        for(int i = 0; i < NUM_CATEGORIES; i++) {
            int end = start + subRangeSize - 1;

            if(remainder > 0) {
                end++;
                remainder--;
            }

            subRanges.add(new int[] {start, end});
            start = end + 1;
        }

        return subRanges;
    }

    /**
     * Analyzes information from question entry in survey repo to
     * extract histogram title and sub-ranges.
     * @param surveyId The survey of interest's ID
     * @param questionId The question of interest's ID
     */
    private void extractChartDetails(Integer surveyId, Integer questionId) {

        // Query survey repo for survey of interest
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        // Get question of interest from survey
        NumberRangeQuestion question = null;
        for (Question q : survey.getQuestions()) {
            if (q.getId() == questionId) {
                question = (NumberRangeQuestion) q;
                break;
            }
        }

        // Create dataset of responses
        if(question != null) {
            this.title = question.getQuestionText();

            // Break data range into sub-ranges
            ArrayList<int[]> subRanges = this.getSubRanges(question.getLowerBound(),
                    question.getUpperBound());

            // Add count each response value within each subrange
            ArrayList<NumberRangeResponse> responses = question.getResponses();
            ArrayList<Integer> responseCount = new ArrayList<Integer>();

            for (int[] sr : subRanges) {
                int count = 0;

                for (NumberRangeResponse r: responses) {
                    if (r.getResponse() >= sr[0] && r.getResponse() <= sr[1]) {
                        count++;
                    }
                }

                responseCount.add(count);
            }

            // Add responses and corresponding counts to dataset
            for(int i = 0; i < NUM_CATEGORIES; i++) {
                String rangeTitle = subRanges.get(i)[0] + "-" + subRanges.get(i)[1];
                this.dataset.addValue(responseCount.get(i), "Range", rangeTitle );
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

        // Generate chart
        JFreeChart chart = ChartFactory.createBarChart(
                this. title,
                "Sub-Range",
                "Count",
                this.dataset);

        // Convert to PNG
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
