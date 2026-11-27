/**
 * This class is responsible for validating operations performed on a survey object.
 * functions in this class will be used for both response and creation controllers
 */

package sysc4806.project.controller;

import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.TextQuestion;
import sysc4806.project.survey.questions.MultipleChoiceQuestion;
import sysc4806.project.survey.responses.MultipleChoiceResponse;
import sysc4806.project.survey.responses.NumberRangeResponse;
import sysc4806.project.survey.responses.Response;
import sysc4806.project.survey.responses.TextResponse;
import sysc4806.project.survey.toggleQuestions.MultipleChoiceToggleQuestion;
import sysc4806.project.survey.toggleQuestions.NumberRangeToggleQuestion;
import sysc4806.project.survey.toggleQuestions.TextToggleQuestion;


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
        } else if (question instanceof TextToggleQuestion textQuestion) {
            if (textQuestion.getQuestionText() == null) {
                return "Missing value";
            }
            return null;
        } else if (question instanceof NumberRangeToggleQuestion rangeQuestion) {
            // Check fields for null
            if (rangeQuestion.getLowerBound() == null || rangeQuestion.getUpperBound() == null || rangeQuestion.getQuestionText() == null) {
                return "Missing value";
            }
            if (rangeQuestion.getLowerBound() >= rangeQuestion.getUpperBound()) {
                return "LowerBound cannot be >= UpperBound";
            }
        } else if (question instanceof MultipleChoiceToggleQuestion choiceQuestion) {
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

    public static String checkResponse(Question question, Response response) {
        if (response instanceof TextResponse textResponse) {
            if(textResponse.getResponse() == null) {
                return "Missing response";
            }
        } else if(response instanceof NumberRangeResponse numberResponse) {
            NumberRangeQuestion numberQuestion = (NumberRangeQuestion) question;
            if(numberResponse.getResponse() > numberQuestion.getUpperBound()
            || numberResponse.getResponse() < numberQuestion.getLowerBound()) {
                return "Response outside of allowed range";
            }
        } else if(response instanceof MultipleChoiceResponse mcqResponse) {
            MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
            if(mcqResponse.getResponse() > mcq.getChoices().size() || mcqResponse.getResponse() < 0) {
                return "Invalid multiple choice selection";
            }
        }
        return null;
    }
}
