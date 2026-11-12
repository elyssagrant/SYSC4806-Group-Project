package sysc4806.project;

import java.util.*;

public class Survey {

    // *** Constructors ***
    private ArrayList<Question> surveyQuestions;

    public Survey(){
        surveyQuestions = new ArrayList<>();
    }

    // *** GETTERS ***

    public ArrayList<Question> getSurveyQuestions(){
        return surveyQuestions;
    }

    public Question getQuestionFromIndex(int index){
        return surveyQuestions.get(index);
    }

    // *** SETTERS ***

    public void setSurveyQuestions(ArrayList<Question> newSurveyQuestions) {
        this.surveyQuestions = newSurveyQuestions;
    }

    // *** ADDITIONAL METHODS ***


}
