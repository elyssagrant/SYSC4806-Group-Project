package sysc4806.project.survey.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TextResponseTest {
    TextResponse response1;
    TextResponse response2;

    @BeforeEach
    void setup(){
        response1 = new TextResponse();
        response2 = new TextResponse();
    }

    @Test
    void test_setAndGetId(){
        response1.setId(0);
        response2.setId(2);

        assertEquals(0, response1.getId());
        assertEquals(2, response2.getId());

        response2.setId(3);
        assertEquals(3, response2.getId());
        assertNotEquals(response1.getId(), response2.getId());
    }

    @Test
    void test_getAndSetResponse(){
        String str1 = "Works well";
        String str2 = "Squeaky wheel";
        response1.setResponse(str1);
        response2.setResponse(str2);

        assertEquals(str1, response1.getResponse());
        assertEquals(str2, response2.getResponse());

        String str3 = "A bit small";
        response1.setResponse(str3);
        assertEquals(str3, response1.getResponse());
        assertNotEquals(response1.getResponse(), response2.getResponse());
    }
}
