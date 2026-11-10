package sysc4806.project;

import java.util.*;

public class TextQuestion implements Question{

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
