package sysc4806.project.charts;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.MultipleChoiceQuestion;
import sysc4806.project.survey.questions.Question;
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

    public PieChartGenerator() {
        this.dataset = new DefaultPieDataset<>();
        this.title = "";
    }

    /**
     * Analyzes information from question entry in survey repo to
     * extract pie chart title and frequencies.
     */
    private void extractChartDetails(MultipleChoiceQuestion question) {
        // Ensure dataset is clear of any previous question's data
        this.dataset.clear();

        // Create dataset of responses
        this.title = question.getQuestionText();

        // Count instances of each response
        ArrayList<MultipleChoiceResponse> responses =  new ArrayList<>(question.getResponses());
        HashMap<String, Integer> responseCount = new HashMap<>();
        for (MultipleChoiceResponse r : responses) {
            responseCount.put(r.getResponse().toString(), responseCount.getOrDefault(r.getResponse(), 0) + 1);
        }

        // Add responses and corresponding counts to dataset
        for(Map.Entry<String, Integer> entry : responseCount.entrySet()) {
            this.dataset.setValue(entry.getKey(), entry.getValue());
        }

    }

    /**
     * Generates a pie chart representation for a particular
     * question's responses.
     *
     * @param question The question of interest
     */
    @Override
    public byte[] generateChart(Question question) {

        // Cast to multiple choice question
        MultipleChoiceQuestion mcq;

        if(question instanceof MultipleChoiceQuestion) {
            mcq = (MultipleChoiceQuestion) question;
        } else {
            return null;
        }

        // Get dataset for chart
        extractChartDetails(mcq);

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
