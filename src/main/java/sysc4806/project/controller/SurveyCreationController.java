package sysc4806.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.Question;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/surveycreation")
public class SurveyCreationController {

//    // The reason this class exists is because the json
//    // format of the schema contains a "type" value, which lets us cast
//    //
//    public static TypedQuestion {
//
//    }

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
        try {
            Survey survey = new Survey();
            survey.setTitle(name);
            survey.setIsOpen(true);
            survey = surveyRepo.save(survey);
            model.addAttribute("survey", survey);
            body = Map.of("id", Integer.toString(survey.getId()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(body);
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
                System.out.println("There was a problem: " + e);
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
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
