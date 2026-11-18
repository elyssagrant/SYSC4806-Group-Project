/**
 * This class is responsible for validating operations performed on a survey object.
 * functions in this class will be used for both response and creation controllers
 */

package sysc4806.project.controller;

import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.TextQuestion;
import sysc4806.project.survey.questions.MultipleChoiceQuestion;


public class SurveyEditValidator {
    

    public static String checkQuestion(Question question) {
        if (question.getId() == null) {
            return "Question missing id";
        }
        if (question instanceof TextQuestion textQuestion) {
            if (textQuestion.getQuestionText() == null) {
                return "Missing value";
            }
            return null;
        } else if (question instanceof NumberRangeQuestion rangeQuestion) {
            // Check fields for null
            if (rangeQuestion.getLowerBound() == null || rangeQuestion.getUpperBound() == null || rangeQuestion.getQuestionText() == null) {
                return "Missing value";
            }
            if (rangeQuestion.getLowerBound() >= rangeQuestion.getUpperBound()) {
                return "LowerBound cannot be >= UpperBound";
            }
        } else if (question instanceof MultipleChoiceQuestion choiceQuestion) {
            // Check fields for null
            if (choiceQuestion.getChoices() == null || choiceQuestion.getQuestionText() == null) {
                return "Missing value";
            }
            if (choiceQuestion.getChoices().size() < 2) {
                return "Number of choices may not be less than 2";
            }
        } else {
            return "Unknown Question Type";
        }
        return null;
    }
}
