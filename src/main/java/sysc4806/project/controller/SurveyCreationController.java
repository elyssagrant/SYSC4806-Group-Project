package sysc4806.project.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.Question;


@Controller
@RequestMapping("/surveycreation")
public class SurveyCreationController {
    
    // Survey repository for database access
    private final SurveyRepository surveyRepo;

    // Constructor and initialize survey repository
    public SurveyCreationController(SurveyRepository surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    // Create a new survey with the given title
    @PostMapping
    public ResponseEntity<Map<String,String>> createNewSurvey(
            @RequestParam(name = "name", required = true) String name,
            Model model ){
        Map<String,String> body;
        int id;
        try {
            Survey survey = new Survey();
            survey.setTitle(name);
            survey.setIsOpen(true);
            survey = surveyRepo.save(survey);
            model.addAttribute("survey", survey);
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
    public ResponseEntity<Void> addQuestionToSurvey(
            @PathVariable Integer id,
            @RequestBody Question[] questions,
            Model model ) {
        try {
            Survey survey = surveyRepo.findById(id).orElseThrow();  // load survey from database
            try {
                // Add question(s) to survey
                for  (Question question : questions) {
                    survey.addQuestion(question);
                }

                surveyRepo.save(survey);                                // save change
                model.addAttribute("survey", survey);
            } catch (Exception e) {
                System.out.println("There was a problem: " + e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(URI.create("/surveycreation/" + id)).body(null);
    }

    // Delete a survey with the given ID
    public String deleteSurvey(Integer id, Model model) {
        // Not for Milestone 1
        return null;
    }

    // Delete a question from the survey with the given ID
    public String deleteQuestion(Question question, Model model) {
        // Not for Milestone 1
        return null;
    }

}
