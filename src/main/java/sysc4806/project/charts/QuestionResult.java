package sysc4806.project.charts;

import java.util.List;

/**
 * Used to store the results of a single question from a closed survey. Contains a byte array image for NRQ and MCQ,
 * and list of text responses for text questions.
 */
public class QuestionResult {
    private final Integer questionId;
    private final String type;
    private final String questionText;
    private final String chart;
    private final List<String> textResponses;

    public QuestionResult(Integer questionId, String type, String questionText,
                          String chart, List<String> textResponses) {
        this.questionId = questionId;
        this.type = type;
        this.questionText = questionText;
        this.chart = chart;
        this.textResponses = textResponses;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public String getType() {
        return type;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getChart() {
        return chart;
    }

    public List<String> getTextResponses() {
        return textResponses;
    }
}
