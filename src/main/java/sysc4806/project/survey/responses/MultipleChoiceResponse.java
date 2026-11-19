package sysc4806.project.survey.responses;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MultipleChoiceResponse implements Response {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Integer corresponding to the bullet point picked from the MCQ question. Uses 0-based indexing.
     */
    private Integer response;

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
     * Returns the response picked.
     * @return An integer corresponding to the bullet point selected (0-based indexing)
     */
    public Integer getResponse() {
        return response;
    }

    /**
     * Sets the stored response.
     * @param response The new bullet point selected.
     */
    public void setResponse(Integer response) {
        this.response = response;
    }
}
