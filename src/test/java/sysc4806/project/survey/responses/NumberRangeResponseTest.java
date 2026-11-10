package sysc4806.project.survey.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NumberRangeResponseTest {


    NumberRangeResponse response1;
    NumberRangeResponse response2;

    @BeforeEach
    void setup(){
        response1 = new NumberRangeResponse();
        response2 = new NumberRangeResponse();
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
    void test_setAndGetResponse() {
        response1.setResponse(3);
        response2.setResponse(5);

        assertEquals(3, response1.getResponse());
        assertEquals(5, response2.getResponse());

        response2.setResponse(14);
        assertEquals(14, response2.getResponse());
        assertNotEquals(response1.getResponse(), response2.getResponse());
    }

    @Test
    void test_idAndResponseDifferent(){
        response1.setResponse(1);
        response1.setId(2);
        assertNotEquals(response1.getId(), response1.getResponse());
    }
}
