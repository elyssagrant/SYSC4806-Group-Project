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
    @JoinColumn(name = "response_id")
    private List<Question> questions;

    // This is a read-only map that allows us to do operations in O(1) instead of iterating through a list
    // It gets loaded whenever we take something out of the database
    @Transient 
    private Map<Integer, Question> questionMap;

    public Survey(){
        questionMap = new HashMap<>();
        isOpen = false;
        title = "";
        version = 0;
    }

    public Survey(String title){
        questionMap = new HashMap<>();
        isOpen = false;
        this.title = title;
        version = 0;
    }

    @PostLoad 
    private void buildMap() {
        questionMap = new HashMap<>();
        for (Question q : questions) {
            questionMap.put(q.getId(), q);
        }
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

    public Map<Integer, Question> getQuestionMap() {
        return questionMap;
    }

    public List<Question> getQuestions() {
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

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // *** ADDITIONAL METHODS ***

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    // The reason I have to replace each field individually is because
    // doing a create and delete at the same time throws errors
    public void replaceQuestion(Question question) {
        Question replacedQuestion = this.questionMap.get(question.getId());
        if (replacedQuestion == null) {
            throw new IllegalArgumentException(
                        "No question with id " + question.getId() + "in survey " + this.id);
        }
        replacedQuestion.copy(question);
    }

    public void deleteQuestion(Question question) {
        Question deletedQuestion = this.questionMap.get(question.getId());
        if (deletedQuestion == null) {
             new IllegalArgumentException(
                        "No question with id " + question.getId() + "in survey " + this.id);
        }
        this.questions.removeIf(q -> Objects.equals(q.getId(), question.getId()));
    }

    // Should be called on making an edit to the survey.
    public void incrementVersion() {
        this.version++;
    }

    public Question findQuestionById(Integer id) {
        for(Question question: questions) {
            if(Objects.equals(question.getId(), id)){
                return question;
            }
        }
        return null;
    }

}
