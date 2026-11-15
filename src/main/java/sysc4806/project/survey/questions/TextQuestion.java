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
import sysc4806.project.survey.responses.TextResponse;

@Entity
public class TextQuestion extends Question {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "response_id")
    private List<TextResponse> responses;

    private String questionText;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public List<TextResponse> getResponses(){
        return responses;
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

    public void copy(Question question) {
        if (!(question instanceof TextQuestion t)) {
            throw new IllegalArgumentException(
                    "Expected MultipleChoiceQuestion, got " + question.getClass().getSimpleName());
        }
        this.setQuestionText(t.getQuestionText());
    }
}
