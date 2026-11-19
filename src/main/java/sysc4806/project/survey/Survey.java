package sysc4806.project.survey;

import java.util.*;

import jakarta.persistence.*;
import sysc4806.project.survey.questions.Question;

@Entity
public class Survey {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private Boolean isOpen;

    // This value is used to guard against invalid edits.
    private int version;


    /**
        This makes sure that all questions inside this list are persisted, and that if the survey gets removed,
        so do the owned questions.
        The join column adds a foreign key to the questions
    */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ElementCollection
    @CollectionTable(name="surveyQuestions", joinColumns=@JoinColumn(name = "survey_id"))
    private Map<Integer, Question> questions;

    public Survey(){
        questions = new HashMap<>();
        isOpen = false;
        title = "";
        version = 0;
    }

    public Survey(String title){
        questions = new HashMap<>();
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

    public Map<Integer, Question> getQuestions() {
        return questions;
    }

    public int getVersion() {
        return version;
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

    public void setQuestions(Map<Integer, Question> questions) {
        this.questions = questions;
    }

    // *** ADDITIONAL METHODS ***

    public void addQuestion(Question question) {
        this.questions.put(question.getId(), question);
    }

    // The reason I have to replace each field individually is because 
    // doing a create and delete at the same time throws errors
    public void replaceQuestion(Question question) {
        if(!questions.containsKey(question.getId())){
            throw new IllegalArgumentException("No question with given id present in survey");
        }
        this.questions.put(question.getId(), question);
    }

    public void deleteQuestion(Question question) {
        Question result = this.questions.remove(question.getId());
        if (result == null) {
            throw new IllegalArgumentException("No question with given id " + question.getId() + " present in survey");
        }
    }

    // Should be called on making an edit to the survey.
    public void incrementVersion() {
        this.version++;
    }

}
