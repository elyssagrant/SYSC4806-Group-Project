package sysc4806.project.survey.questions;

import sysc4806.project.survey.responses.NumberRangeResponse;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class NumberRangeQuestion extends Question {

    private ArrayList<NumberRangeResponse> responses;
    private String questionText;

    @Id
    @GeneratedValue
    private Integer id;

    private Integer lowerBound;
    private Integer upperBound;

    //*** Constructors ***

    public NumberRangeQuestion(){
        responses = new ArrayList<>();
        lowerBound = 0;
        upperBound = 0;
        questionText = "";
    }

    public NumberRangeQuestion(String questionText){
        responses = new ArrayList<>();
        lowerBound = 0;
        upperBound = 0;
        this.questionText = questionText;
    }

    // *** Getters ***

    public String getQuestionText(){
        return questionText;
    }

    public ArrayList<NumberRangeResponse> getResponses(){
        return responses;
    }

    public Integer getLowerBound(){
        return lowerBound;
    }

    public Integer getUpperBound(){
        return upperBound;
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

    public void setUpperBound(Integer newUpperBound){
        this.upperBound = newUpperBound;
    }

    public void setLowerBound(Integer newLowerBound){
        this.lowerBound = newLowerBound;
    }

    // *** Additional Methods ***

    public void addResponse(NumberRangeResponse response){
        responses.add(response);
    }

}
