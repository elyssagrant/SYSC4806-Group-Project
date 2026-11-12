package sysc4806.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.Question;

import java.util.HashMap;
import java.util.Map;



@Controller
@RequestMapping("/survey")
public class SurveyResponseController {

    //Need to use a record since when serializing a normal map<string,string> doesn't work for the questions array
    public record SerializedSurvey(String name, Question[] questions) {}

    // Survey repository for database access
    private final SurveyRepository surveyRepo;

    // Constructor
    public SurveyResponseController(SurveyRepository surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    // Show the survey with the given ID
    @GetMapping("/{id}")
    public ResponseEntity<SerializedSurvey> showSurvey(@PathVariable Integer id, Model model) {
    SerializedSurvey response;
        try {
            Survey survey = surveyRepo.findById(id).orElseThrow(); // load survey from database
            model.addAttribute("survey", survey);
            Question[] questions = survey.getQuestions().toArray(new Question[0]);
            response = new SerializedSurvey(survey.getTitle(), questions);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // Not for Milestone 1
    public String openNewSurvey(Integer id, Model model) {
        return null;
    }

    // Not for Milestone 1
    public String submitResponse(Integer id, Model model) {
        return null;
    }
}
