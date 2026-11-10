package sysc4806.project.survey;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import sysc4806.project.survey.questions.Question;

@Entity
public class Survey {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private Boolean isOpen;

    // NOTE: Question being an interface is preventing JPA from saving it to the database. 
    // The way I understand this works is that we need to use @OneToMany to make it so a
    // survey holds multiple questions, but we can't do that because Question is an interface
    // instead of an abstract class. Currently @Transient makes so no questions persist in the
    // database.
    @Transient 
    private ArrayList<Question> questions = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}
