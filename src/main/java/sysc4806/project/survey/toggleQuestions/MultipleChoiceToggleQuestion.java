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
import sysc4806.project.survey.responses.MultipleChoiceResponse;
import sysc4806.project.FeatureToggling.MultipleChoiceToggle;

@Component("newMultipleChoice")
@Entity
public class MultipleChoiceToggleQuestion extends Question implements MultipleChoiceToggle {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "response_id")
    private List<MultipleChoiceResponse> responses;


    private ArrayList<String> choices;
    private String questionText;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //*** Constructors ***

    public MultipleChoiceToggleQuestion(){
        responses = new ArrayList<>();
        choices = new ArrayList<>();
        questionText = "";
    }

    public MultipleChoiceToggleQuestion(String questionText){
        responses = new ArrayList<>();
        choices = new ArrayList<>();
        this.questionText = questionText;
    }

    // *** Getters ***

    public String getQuestionText(){
        return questionText;
    }

    public List<MultipleChoiceResponse> getResponses(){
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

    public void copy(Question question) {
        if (!(question instanceof sysc4806.project.survey.toggleQuestions.MultipleChoiceToggleQuestion mc)) {
            throw new IllegalArgumentException(
                    "Expected MultipleChoiceQuestion, got " + question.getClass().getSimpleName());
        }
        this.setChoices(mc.getChoices());
        this.setQuestionText(mc.getQuestionText());
    }

    @Override
    public void execute() {

    }
}
