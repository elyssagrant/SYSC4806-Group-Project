package sysc4806.project.survey.questions;

import sysc4806.project.survey.responses.MultipleChoiceResponse;

import java.util.ArrayList;

public class MultipleChoiceQuestion implements Question {


    private int id;
    private ArrayList<MultipleChoiceResponse> responses;
    private String questionText;

    //*** Constructors ***

    public MultipleChoiceQuestion(){
        responses = new ArrayList<>();
        questionText = "";
    }

    public MultipleChoiceQuestion(String questionText){
        responses = new ArrayList<>();
        this.questionText = questionText;
    }

    // *** Interface ***
    public int getId() {
        return id;
    }
    public void setId(int id) {}

    // *** Getters ***

    public String getQuestionText(){
        return questionText;
    }

    public ArrayList<MultipleChoiceResponse> getResponses(){
        return responses;
    }

    public MultipleChoiceResponse getResponseFromIndex(int index){
        return responses.get(index);
    }

    // *** Setters ***

    public void setResponses(ArrayList<MultipleChoiceResponse> newResponses){
        this.responses = newResponses;
    }

    public void setQuestionText(String newQuestionText){
        this.questionText = newQuestionText;
    }

    // *** Additional Methods ***

    public void addResponse(MultipleChoiceResponse response){
        responses.add(response);
    }
}
