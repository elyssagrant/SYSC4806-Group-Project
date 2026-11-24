package sysc4806.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sysc4806.project.charts.MetricsGenerator;
import sysc4806.project.survey.SurveyRepository;

import java.util.ArrayList;
import java.util.Base64;
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
    public ResponseEntity<List<String>> getMetrics(
            @PathVariable Integer id) {

        MetricsGenerator metricsGenerator = new MetricsGenerator(surveyRepo);
        List<String> response = metricsGenerator.generateMetrics(id);

        return ResponseEntity.ok(response);
    }
}
