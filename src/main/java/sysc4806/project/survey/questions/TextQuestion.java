package sysc4806.project.survey.questions;

import sysc4806.project.survey.responses.TextResponse;

import java.util.*;

public class TextQuestion implements Question {

    private ArrayList<TextResponse> responses;
    private String questionText;
    private Integer id;

    //*** Constructors ***

    public TextQuestion(){
        responses = new ArrayList<>();
        questionText = "";
    }

    public TextQuestion(String questionText){
        responses = new ArrayList<>();
        this.questionText = questionText;
    }

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

    public Integer getId(){
        return this.id;
    }

    // *** Setters ***

    public void setResponses(ArrayList<TextResponse> newResponses){
        this.responses = newResponses;
    }

    public void setQuestionText(String newQuestionText){
        this.questionText = newQuestionText;
    }

    public void setId(Integer newId){
        this.id = newId;
    }

    // *** Additional Methods ***

    public void addResponse(TextResponse response){
        responses.add(response);
    }

}
