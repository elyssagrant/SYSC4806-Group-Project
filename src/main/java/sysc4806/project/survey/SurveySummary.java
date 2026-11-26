package sysc4806.project.survey;

import java.util.List;
import jakarta.persistence.*;

public class SurveySummary {
    
    private Integer id;
    private String title;
    private Boolean isOpen;
    private Integer numberOfResponses;
    private Integer numberOfQuestions;

    public SurveySummary(Survey survey) {
        this.id = survey.getId();
        this.title = survey.getTitle();
        this.isOpen = survey.getIsOpen();
        this.numberOfResponses = survey.getNumberOfResponses();
        this.numberOfQuestions = survey.getQuestions().size();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public Integer getNumberOfResponses() {
        return numberOfResponses;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }
}