package sysc4806.project.survey.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;

@SpringBootTest
@AutoConfigureRestTestClient
public class SurveyCreationControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private SurveyRepository repo;


    @Test
    void contextLoads(){
        //Intentionally empty
    }

    @Test
    void createForm() {

    }

    @Test
    void addQuestion() {

    }

    private static Survey createSurvey() {
        return new Survey("Spam spam bacon eggs and spam");
    }
}
