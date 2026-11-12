package sysc4806.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;

@Controller
@RequestMapping("/survey")
public class SurveyResponseController {

    // Survey repository for database access
    private final SurveyRepository surveyRepo;

    // Constructor
    public SurveyResponseController(SurveyRepository surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    // Show the survey with the given ID
    @GetMapping("/{id}")
    public String showSurvey(@PathVariable Integer id, Model model) {
        Survey survey = surveyRepo.findById(id).orElseThrow();  // load survey from database
        model.addAttribute("survey", survey);
        return "Survey";
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
