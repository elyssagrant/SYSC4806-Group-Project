package sysc4806.project.survey.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import sysc4806.project.controller.SurveyCreationController;
import sysc4806.project.survey.Survey;
import sysc4806.project.survey.SurveyRepository;
import sysc4806.project.survey.questions.MultipleChoiceQuestion;
import sysc4806.project.survey.questions.NumberRangeQuestion;
import sysc4806.project.survey.questions.Question;
import sysc4806.project.survey.questions.TextQuestion;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    void addQuestion_validCall() {
        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(repo.findById(any())).thenReturn(Optional.of(createSurvey()));

        ArrayList<Question> newQuestions = new ArrayList<>();
        TextQuestion newQ = new TextQuestion("What is your favourite type of spam?");
        newQ.setId(8);
        newQuestions.add(newQ);
        SurveyCreationController.QuestionChanges changes = new SurveyCreationController.QuestionChanges(1, newQuestions, new ArrayList<>(), new ArrayList<>());

        restTestClient.post().uri("/survey/1/questions")
                .body(changes)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void deleteQuestion_validCall() {
        Survey miniSurvey = Mockito.mock(Survey.class);
        when(miniSurvey.getId()).thenReturn(1);
        when(miniSurvey.getIsOpen()).thenReturn(true);
        when(miniSurvey.getVersion()).thenReturn(1);
        TextQuestion q = new TextQuestion("What is the most important thing to remember?");
        q.setId(1);
        Mockito.doNothing().when(miniSurvey).deleteQuestion(q);

        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(repo.findById(any())).thenReturn(Optional.of(miniSurvey));

        ArrayList<Question> deleteQuestions = new ArrayList<>();
        deleteQuestions.add(q);
        SurveyCreationController.QuestionChanges changes = new SurveyCreationController.QuestionChanges(1, new ArrayList<>(), new ArrayList<>(), deleteQuestions);

        restTestClient.post().uri("/survey/1/questions")
                .body(changes)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    private static Survey createSurvey() {
        Survey survey = new Survey("Spam!");
        survey.setId(1);
        survey.setQuestions(new ArrayList<>());
        survey.setIsOpen(true);
        survey.incrementVersion();
        TextQuestion question1 = new TextQuestion("What you got, then?");
        question1.setId(1);
        NumberRangeQuestion question2 = new NumberRangeQuestion("How many spam would you like?");
        question2.setId(2);
        question2.setLowerBound(1);
        question2.setUpperBound(1000);
        MultipleChoiceQuestion question3 = new MultipleChoiceQuestion("What are you getting?");
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Egg and bacon");
        choices.add("Egg, sausage and bacon");
        choices.add("Egg and spam");
        choices.add("Egg, bacon and spam");
        choices.add("Egg, bacon, sausage and spam");
        choices.add("Spam, bacon, sausage and spam");
        choices.add("Spam, egg, spam, spam, bacon and spam");
        choices.add("Spam, spam, spam, egg and spam");
        choices.add("Spam, spam, spam, spam, baked beans, spam, spam, spam, spam");
        question3.setChoices(choices);
        question3.setId(3);
        survey.addQuestion(question1);
        survey.addQuestion(question2);
        survey.addQuestion(question3);
        return survey;
    }
}
