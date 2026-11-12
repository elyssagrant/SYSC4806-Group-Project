package sysc4806.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // Show the survey creation page
    @GetMapping
        public String showCreationPage() {
        return "SurveyCreation";
    }

    // Create a new survey with the given title
    @PostMapping
    public String createNewSurvey(@RequestParam String title, Model model){
        Survey survey = new Survey();
        survey.setTitle(title);
        survey.setIsOpen(true);

        survey = surveyRepo.save(survey);

        model.addAttribute("survey", survey);
        return "SurveyCreation";
    }

    // Add a question to the survey with the given ID
    //
    // IMPORTANT NOTE: while testing on my local system, I was not able to save any
    // Question objects to the database. I asked AI, and it explained that this is because
    // Question.java is an interface, and JPA cannot save interfaces to the database. It
    // suggested we should turn Question into an abstract class rather than an interface.
    // Want to discuss this potential change with group. Would also need to add a switch case
    // between question types.
    @PostMapping("/{id}/questions")
    public String addQuestionToSurvey(@PathVariable Integer id, @ModelAttribute Question question, Model model) {
        Survey survey = surveyRepo.findById(id).orElseThrow();  // load survey from database
        survey.addQuestion(question);                           // add question to survey
        surveyRepo.save(survey);                                // save change
        model.addAttribute("survey", survey);
        return "SurveyCreation";
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
