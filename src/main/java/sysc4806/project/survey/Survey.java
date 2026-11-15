package sysc4806.project.survey;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import sysc4806.project.survey.questions.Question;

@Entity
public class Survey {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private Boolean isOpen;


    /**
        This makes sure that all questions inside this list are persisted, and that if the survey gets removed,
        so do the owned questions.
        The join column adds a foreign key to the questions
    */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "survey_id")
    private List<Question> questions;

    public Survey(){
        questions = new ArrayList<>();
        isOpen = false;
        title = "";
    }

    public Survey(String title){
        questions = new ArrayList<>();
        isOpen = false;
        this.title = title;
    }

    // *** GETTERS ***

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    // *** SETTERS ***

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    // *** ADDITIONAL METHODS ***

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

}
