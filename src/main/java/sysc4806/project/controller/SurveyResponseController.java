package sysc4806.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.MultipleChoiceQuestion;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.questions.TextQuestion;
import sysc4806.project.survey.responses.MultipleChoiceResponse;
import sysc4806.project.survey.responses.NumberRangeResponse;
import sysc4806.project.survey.responses.TextResponse;



@Controller
@RequestMapping("/survey")
public class SurveyResponseController {

    //Need to use a record since when serializing a normal map<string,string> doesn't work for the questions array
    public record SerializedSurvey(String name, int version, Question[] questions) {}

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
            response = new SerializedSurvey(survey.getTitle(), survey.getVersion(), questions);
        } catch (Exception e) {
            System.out.println("There was an error sending the survey: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // Not for Milestone 1
    public String openNewSurvey(Integer id, Model model) {
        return null;
    }

    // Not for Milestone 1
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> submitResponse(@PathVariable Integer id,
                                               @RequestBody TextResponse[] textResponses,
                                               @RequestBody NumberRangeResponse[] numberResponses,
                                               @RequestBody MultipleChoiceResponse[] mcqResponses,
                                               Model model) {
        try {
            Survey survey = surveyRepo.findById(id).orElseThrow();

            int textIndex = 0;
            int numberIndex = 0;
            int mcqIndex = 0;

            for(int i = 0; i < survey.getQuestions().size(); i++) {
                Question question = survey.getQuestions().get(i);
                if (question instanceof TextQuestion textQ) {
                    textQ.addResponse(textResponses[textIndex]);
                    textIndex ++;
                }
                else if (question instanceof NumberRangeQuestion rangeQ) {
                    rangeQ.addResponse(numberResponses[numberIndex]);
                    numberIndex ++;
                }
                else if (question instanceof MultipleChoiceQuestion multiChoiceQ) {
                    multiChoiceQ.addResponse(mcqResponses[mcqIndex]);
                    mcqIndex ++;
                }
            }

            surveyRepo.save(survey);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(null);
    }
}
