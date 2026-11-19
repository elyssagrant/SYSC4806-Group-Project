package sysc4806.project.survey.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import sysc4806.project.controller.SurveyResponseController;
import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.questions.TextQuestion;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureRestTestClient
public class SurveyResponseControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private SurveyRepository repo;



//    @Test
//    void obtainResponse_validResponse_savesSurvey() {
//        //Create the survey
//        Survey survey = new Survey("Test");
//        Question question1 = new TextQuestion("What is your name?");
//        Question question2 = new TextQuestion("What is your favourite colour?");
//        Question question3 = new NumberRangeQuestion("What is the air speed velocity of an unladen swallow?");
//        survey.addQuestion(question1);
//        survey.addQuestion(question2);
//        survey.addQuestion(question3);
//        Map<String, String> body = Map.of("id", Integer.toString(1));
//
//        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);;
//        when(repo.findById(any())).thenReturn(Optional.of(survey));
//
//        restTestClient.get().uri("/survey/1")
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(SurveyResponseController.SerializedSurvey.class);
//    }
}

