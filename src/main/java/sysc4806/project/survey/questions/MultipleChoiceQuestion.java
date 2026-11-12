package sysc4806.project.survey.questions;

import sysc4806.project.survey.responses.MultipleChoiceResponse;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MultipleChoiceQuestion extends Question {


    private ArrayList<MultipleChoiceResponse> responses;
    private ArrayList<String> choices;
    private String questionText;

    @Id
    @GeneratedValue
    private Integer id;

    //*** Constructors ***

    public MultipleChoiceQuestion(){
        responses = new ArrayList<>();
        choices = new ArrayList<>();
        questionText = "";
    }

    public MultipleChoiceQuestion(String questionText){
        responses = new ArrayList<>();
        choices = new ArrayList<>();
        this.questionText = questionText;
    }

    // *** Getters ***

    public String getQuestionText(){
        return questionText;
    }

    public ArrayList<MultipleChoiceResponse> getResponses(){
        return responses;
    }

    public ArrayList<String> getChoices(){
        return choices;
    }

    public Integer getId(){
        return this.id;
    }

    // *** Setters ***

    public void setResponses(ArrayList<MultipleChoiceResponse> newResponses){
        this.responses = newResponses;
    }

    public void setQuestionText(String newQuestionText){
        this.questionText = newQuestionText;
    }

    public void setChoices(ArrayList<String> newChoices){
        this.choices = newChoices;
    }

    public void setId(Integer newId){
        this.id = newId;
    }

    // *** Additional Methods ***

    public void addResponse(MultipleChoiceResponse response){
        responses.add(response);
    }


}
