package sysc4806.project.charts;

import java.util.List;

public class QuestionResult {
    private final Integer questionId;
    private final String type;
    private final String questionText;
    private final byte[] chart;
    private final List<String> textResponses;

    public QuestionResult(Integer questionId, String type, String questionText,
                          byte[] chart, List<String> textResponses) {
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

    public byte[] getChart() {
        return chart;
    }

    public List<String> getTextResponses() {
        return textResponses;
    }
}
