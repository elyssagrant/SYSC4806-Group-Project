package sysc4806.project.survey.questions;

import sysc4806.project.survey.responses.TextResponse;

import java.util.*;

public class TextQuestion implements Question {

    private int id;
    private ArrayList<TextResponse> responses;
    private String questionText;

    //*** Constructors ***

    public TextQuestion(){
        responses = new ArrayList<>();
        questionText = "";
    }

    public TextQuestion(String questionText){
        responses = new ArrayList<>();
        this.questionText = questionText;
    }

    // *** Interface ***
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    // *** Getters ***

    public String getQuestionText(){
        return questionText;
    }

    public ArrayList<TextResponse> getResponses(){
        return responses;
    }

    public TextResponse getResponseFromIndex(int index){
        return responses.get(index);
    }

    // *** Setters ***

    public void setResponses(ArrayList<TextResponse> newResponses){
        this.responses = newResponses;
    }

    public void setQuestionText(String newQuestionText){
        this.questionText = newQuestionText;
    }

    // *** Additional Methods ***

    public void addResponse(TextResponse response){
        responses.add(response);
    }
}
