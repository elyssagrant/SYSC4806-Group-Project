package sysc4806.project.controller;

import org.springframework.http.HttpStatus;
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
import sysc4806.project.survey.responses.Response;
import sysc4806.project.survey.responses.TextResponse;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/survey")
public class SurveyResponseController {

    //Need to use a record since when serializing a normal map<string,string> doesn't work for the questions array
    public record SerializedSurvey(String name, int version, Question[] questions) {}

    public record ResponseRecord(int version, Response[] responses){}

    // Survey repository for database access
    private final SurveyRepository surveyRepo;

    // Constructor
    public SurveyResponseController(SurveyRepository surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    // Show the survey with the given ID
    @GetMapping("/{id}")
    public ResponseEntity<SerializedSurvey> showSurvey(@PathVariable("id") Integer id, Model model) {
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

    // Submit responses for a survey with a given id
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> submitResponse(@PathVariable("id") Integer id,
                                               @RequestBody ResponseRecord responses,
                                               Model model) {
        Map<String, String> errors = new HashMap<>();
        try {
            Survey survey = surveyRepo.findById(id).orElseThrow();
            // Validate version - if the numbers are not the same, then the edit is invalid
            if(!survey.getIsOpen()){
                return ResponseEntity.badRequest().body(Map.of("message", "Cannot submit responses, as survey is closed"));
            }
            if (survey.getVersion() != responses.version()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Invalid form version: An edit may have already been made during this session."));
            }

            for(Response response: responses.responses()) {
                /*
                 * The id of the response needs to be null when saved. The response's given Id will thus
                 * be the ID of the question it belongs to.
                 */
                Question question = survey.findQuestionById(response.getId());
                if(question == null){
                    throw new IllegalStateException("Cannot find related question for given response ID");
                }
                response.setId(null);

                //Check to ensure the response is well-formed
                String error = SurveyEditValidator.checkResponse(question, response);
                if(error != null) {
                    errors.put(question.getId().toString(), error);
                    continue;
                }

                //Add the response to the question
                if (question instanceof TextQuestion) {
                    ((TextQuestion) question).addResponse((TextResponse) response);
                } else if (question instanceof NumberRangeQuestion) {
                    ((NumberRangeQuestion) question).addResponse((NumberRangeResponse) response);
                } else if (question instanceof MultipleChoiceQuestion) {
                    ((MultipleChoiceQuestion) question).addResponse((MultipleChoiceResponse) response);
                } else {
                    throw new IllegalArgumentException("No suitable question format found");
                }
            }

            if(!errors.isEmpty()) {
                errors.put("message", "Some responses were not properly formatted");
                return ResponseEntity.unprocessableContent().body(errors);
            }

            //Save the survey with the new responses
            surveyRepo.save(survey);
        }catch (ClassCastException e) {
            return ResponseEntity.unprocessableContent().body(Map.of("message", "Invalid response format provided for the given survey"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(null);
    }

    @GetMapping
    public ResponseEntity<List<SurveySummary>> listSurveys() {
        List<SurveySummary> summaries = new ArrayList<>();
        for (Survey survey : surveyRepo.findAll()) {
            summaries.add(new SurveySummary(survey));
        }
        return ResponseEntity.ok(summaries);
    }
}


