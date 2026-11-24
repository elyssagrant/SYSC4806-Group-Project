package sysc4806.project.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.Question;


@Controller
@RequestMapping("/survey")
public class SurveyCreationController {
    
    // Survey repository for database access
    private final SurveyRepository surveyRepo;

    // Match the format of the /questions request
    public record QuestionChanges(
        int version,
        List<Question> add,
        List<Question> edit,
        @JsonProperty ("delete") List<Question> remove
    ) {}

    // Constructor and initialize survey repository
    public SurveyCreationController(SurveyRepository surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    // Create a new survey with the given title
    @PostMapping
    public ResponseEntity<Map<String,String>> createNewSurvey(
            @RequestParam(name = "name", required = true) String name){
        Map<String,String> body;
        int id;
        try {
            Survey survey = new Survey();
            survey.setTitle(name);
            survey.setIsOpen(true);
            survey = surveyRepo.save(survey);
            id = survey.getId();
            body = Map.of("id", Integer.toString(id));
        } catch (Exception e) {
            System.out.println("There was an error creating the form: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.created(URI.create("/surveycreation/" + id)).body(body);
    }

    // Add a question to the survey with the given ID
    @PostMapping("/{id}/questions")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addQuestionToSurvey(
            @PathVariable("id") Integer id,
            @RequestBody QuestionChanges changes) {
        try {
            Survey survey = surveyRepo.findById(id).orElseThrow();  // load survey from database

            // Validate version - if the numbers are not the same, then the edit is invalid
            if (survey.getVersion() != changes.version()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Invalid form version: An edit may have already been made during this session."));
            }


            try {
                Map<String,String> questionErrors = new HashMap<>();
                // Add question(s) to survey
                for (Question question : changes.add()) {
                    String message = SurveyEditValidator.checkQuestion(question);
                    if (message != null) {
                        questionErrors.put(String.valueOf(question.getId()), message);
                        continue;
                    }
                    question.setId(null);
                    survey.addQuestion(question);
                }
                // Delete questions(s) from survey
                for (Question question : changes.remove()) {
                    survey.deleteQuestion(question);
                }
                // Edit question(s) from survey (basically a replace operation)
                for (Question question: changes.edit()) {
                    String message = SurveyEditValidator.checkQuestion(question);
                    if (message != null) {
                        questionErrors.put(String.valueOf(question.getId()), message);
                        continue;
                    }
                    survey.replaceQuestion(question);
                }

                // If there were any errors with the fields, return them here
                if (!questionErrors.isEmpty()) {
                    questionErrors.put("message", "Some of the edits submitted were invalid");
                    return ResponseEntity.unprocessableContent().body(questionErrors);
                }

                // Increment survey version before saving
                survey.incrementVersion();
                surveyRepo.save(survey);                                // save change
            } catch (Exception e) {
                System.out.println("There was a problem: " + e.getMessage() + " trace: " + e.getStackTrace() + "cause: "+ e.getCause());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e.getMessage() + " trace: " + e.getStackTrace() + "cause: " + e.getCause());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(URI.create("/surveycreation/" + id)).body(null);
    }

    // Delete a survey with the given ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSurvey(
            @PathVariable Integer id) {
        try {
            Survey survey = surveyRepo.findById(id).orElseThrow();  // load survey from database
            surveyRepo.delete(survey);
        } catch (Exception e) {
            System.out.println("There was a problem: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Survey does not exist"));
        }
        return ResponseEntity.noContent().build();
    }
}
