package sysc4806.project.survey.questions;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import org.springframework.stereotype.Component;
import sysc4806.project.survey.responses.NumberRangeResponse;
import sysc4806.project.FeatureToggling.NumberRangeToggle;

@Component("originalNumberRange")
@Entity
public class NumberRangeQuestion extends Question implements NumberRangeToggle{


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "response_id")
    private List<NumberRangeResponse> responses;

    private String questionText;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public NumberRangeQuestion(String questionText, int upperBound, int lowerBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.questionText = questionText;
    }

    // *** Getters ***

    public String getQuestionText(){
        return questionText;
    }

    public List<NumberRangeResponse> getResponses(){
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

    public boolean withinRange(Integer number){
        if (number>=this.lowerBound && number<=this.upperBound){
            return true;
        }
        return false;
    }

    public void copy(Question question) {
        if (!(question instanceof NumberRangeQuestion nr)) {
            throw new IllegalArgumentException(
                    "Expected NumberRangeQuestion, got " + question.getClass().getSimpleName());
        }
        this.setQuestionText(nr.getQuestionText());
        this.setLowerBound(nr.getLowerBound());
        this.setUpperBound(nr.getUpperBound());
    }

    @Override
    public void execute() {

    }
}
