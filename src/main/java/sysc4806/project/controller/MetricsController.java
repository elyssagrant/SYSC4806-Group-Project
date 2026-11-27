package sysc4806.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sysc4806.project.charts.QuestionResult;
import sysc4806.project.charts.SurveyResultsGenerator;
import sysc4806.project.survey.SurveyRepository;

import java.util.List;

@Controller
@RequestMapping("/survey")
public class MetricsController {

    // Survey repository for database access
    private final SurveyRepository surveyRepo;


    // Constructor and initialize survey repository
    public MetricsController(SurveyRepository surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    @GetMapping("/{id}/metrics")
    public ResponseEntity<List<QuestionResult>> getMetrics(
            @PathVariable Integer id) {

        SurveyResultsGenerator generator = new SurveyResultsGenerator(this.surveyRepo);
        List<QuestionResult> response = generator.generateResults(id);

        return ResponseEntity.ok(response);
    }
}
