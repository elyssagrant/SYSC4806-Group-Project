package sysc4806.project.survey.responses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TextResponse {

    @Id
    @GeneratedValue
    private Integer id;

    private String response;

    //Using default constructor

    /**
     * Getter for the internal id of the response object
     * @return The id of the response
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for the internal id of the response. In practice, this should not be used,
     * as the id is generated and internally managed.
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for the freehand text response.
     * @return The string submitted as the response.
     */
    public String getResponse() {
        return response;
    }

    /**
     * Setter for a new response value.
     * @param response The new string to submit as a response.
     */
    public void setResponse(String response) {
        this.response = response;
    }
}
