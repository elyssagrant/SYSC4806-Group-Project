package sysc4806.project.survey.toggleQuestions;

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
import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.responses.NumberRangeResponse;
import sysc4806.project.FeatureToggling.NumberRangeToggle;

@Component("newNumberRange")
@Entity
public class NumberRangeToggleQuestion extends Question implements NumberRangeToggle{


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "response_id")
    private List<NumberRangeResponse> responses;

    private String questionText;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double lowerBound;
    private double upperBound;

    //*** Constructors ***

    public NumberRangeToggleQuestion(){
        responses = new ArrayList<>();
        lowerBound = 0.0;
        upperBound = 0.0;
        questionText = "";
    }

    public NumberRangeToggleQuestion(String questionText){
        responses = new ArrayList<>();
        lowerBound = 0.0;
        upperBound = 0.0;
        this.questionText = questionText;
    }

    public NumberRangeToggleQuestion(String questionText, double upperBound, double lowerBound) {
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

    public Double getLowerBound(){
        return lowerBound;
    }

    public Double getUpperBound(){
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

    public void setUpperBound(Double newUpperBound){
        this.upperBound = newUpperBound;
    }

    public void setLowerBound(Double newLowerBound){
        this.lowerBound = newLowerBound;
    }

    // *** Additional Methods ***

    public void addResponse(NumberRangeResponse response){
        responses.add(response);
    }

    public boolean withinRange(Double number){
        if (number>=this.lowerBound && number<=this.upperBound){
            return true;
        }
        return false;
    }

    public void copy(Question question) {
        if (!(question instanceof sysc4806.project.survey.toggleQuestions.NumberRangeToggleQuestion nr)) {
            throw new IllegalArgumentException(
                    "Expected NumberRangeToggleQuestion, got " + question.getClass().getSimpleName());
        }
        this.setQuestionText(nr.getQuestionText());
        this.setLowerBound(nr.getLowerBound());
        this.setUpperBound(nr.getUpperBound());
    }

    @Override
    public void execute() {

    }

}
