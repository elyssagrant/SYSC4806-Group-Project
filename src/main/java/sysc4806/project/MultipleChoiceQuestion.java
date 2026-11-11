package sysc4806.project;

import java.util.ArrayList;

public class MultipleChoiceQuestion implements Question{


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
