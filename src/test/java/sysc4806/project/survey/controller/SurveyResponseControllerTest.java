package sysc4806.project.survey.controller;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import sysc4806.project.controller.SurveyResponseController;
import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.questions.TextQuestion;
import sysc4806.project.survey.responses.NumberRangeResponse;
import sysc4806.project.survey.responses.Response;
import sysc4806.project.survey.responses.TextResponse;

import java.util.ArrayList;
import java.util.List;
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


    @Test
    void contextLoads(){
        //Intentionally empty
    }

    @Test
    void obtainResponse_validResponse_savesSurvey() {
        //Create the survey
        Survey survey = generateSurvey();


        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);;
        when(repo.findById(any())).thenReturn(Optional.of(survey));

        TextResponse response1 = new TextResponse();
        response1.setId(1);
        response1.setResponse("John");
        TextResponse response2 = new TextResponse();
        response2.setId(2);
        response2.setResponse("Blue");
        NumberRangeResponse response3 = new NumberRangeResponse();
        response3.setId(3);
        response3.setResponse(3);

        SurveyResponseController.ResponseRecord responseRecord = new
                SurveyResponseController.ResponseRecord(1,
                new Response[]{response1, response2, response3});

        restTestClient.post().uri("/survey/1")
                .body(responseRecord)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isAccepted()
                .expectBody(ResponseEntity.class);
    }

    private static @NonNull Survey generateSurvey() {
        Survey survey = new Survey("Test");
        TextQuestion question1 = new TextQuestion("What is your name?");
        TextQuestion question2 = new TextQuestion("What is your favourite colour?");
        NumberRangeQuestion question3 = new NumberRangeQuestion("What is the air speed velocity of an unladen swallow?");
        question3.setLowerBound(0);
        question3.setUpperBound(5);
        question1.setId(1);
        question2.setId(2);
        question3.setId(3);
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        survey.setQuestions(questions);
        survey.setId(1);
        survey.setIsOpen(true);
        survey.incrementVersion();
        return survey;
    }
}

