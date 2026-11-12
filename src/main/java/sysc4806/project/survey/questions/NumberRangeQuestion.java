package sysc4806.project.survey.questions;

import sysc4806.project.survey.responses.NumberRangeResponse;

import java.util.ArrayList;

public class NumberRangeQuestion implements Question {

    private int id;

    private int lowerBound;
    private int upperBound;
    private String questionText;
    private ArrayList<NumberRangeResponse> responses;

    NumberRangeQuestion(int id, int lowerBound, int upperBound, String questionText) {
        this.id = id;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.questionText = questionText;
        this.responses = new ArrayList<>();
    }

    boolean addResponse(NumberRangeResponse response) {
        this.responses.add(response);
        //TODO: Validation goes here? - idk

        return true;
    }

    boolean withinRange(int value) {
        return value <= this.upperBound && value >= this.lowerBound;
    }

    // Interface
    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}

    // Getters
    public int getLowerBound() {return this.lowerBound;}
    public int getUpperBound() {return this.upperBound;}
    public String getQuestionText() {return this.questionText;}

    // Setters
    public void setLowerBound(int lowerBound) {this.lowerBound = lowerBound;}
    public void setUpperBound(int upperBound) {this.upperBound = upperBound;}
    public void setQuestionText(String questionText) {this.questionText = questionText;}
}
