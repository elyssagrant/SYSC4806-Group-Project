package sysc4806.project.survey.questions;

import sysc4806.project.survey.responses.NumberRangeResponse;

import java.util.ArrayList;

public class NumberRangeQuestion implements Question {

    private ArrayList<NumberRangeResponse> responses;
    private String questionText;
    private Integer id;
    private int[] range;

    private final int MAXRANGEARRAYSIZE = 2;
    private final int UPPERBOUNDINDEX = 1;
    private final int LOWERBOUNDINDEX = 0;

    //*** Constructors ***

    public NumberRangeQuestion(){
        responses = new ArrayList<>();
        range = new int[MAXRANGEARRAYSIZE];
        questionText = "";
    }

    public NumberRangeQuestion(String questionText){
        responses = new ArrayList<>();
        range = new int[MAXRANGEARRAYSIZE];
        this.questionText = questionText;
    }

    // *** Getters ***

    public String getQuestionText(){
        return questionText;
    }

    public ArrayList<NumberRangeResponse> getResponses(){
        return responses;
    }

    public NumberRangeResponse getResponseFromIndex(int index){
        return responses.get(index);
    }

    public int getLowerBound(){
        return range[LOWERBOUNDINDEX];
    }

    public int getUpperBound(){
        return range[UPPERBOUNDINDEX];
    }

    public Integer getId(){
        return this.id;
    }

    // *** Setters ***

    public void setResponses(ArrayList<NumberRangeResponse> newResponses){
        this.responses = newResponses;
    }

    public void setQuestionText(String newQuestionText){
        this.questionText = newQuestionText;
    }

    public void setId(Integer newId){
        this.id = newId;
    }

    public void setUpperBound(int newUpperBound){
        this.range[UPPERBOUNDINDEX] = newUpperBound;
    }

    public void setLowerBound(int newLowerBound){
        this.range[LOWERBOUNDINDEX] = newLowerBound;
    }

    // *** Additional Methods ***

    public void addResponse(NumberRangeResponse response){
        responses.add(response);
    }

}
