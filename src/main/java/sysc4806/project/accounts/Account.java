package sysc4806.project.accounts;

import java.util.List;

import jakarta.persistence.*;
import sysc4806.project.survey.Survey;

@Entity 
public class Account {

    @Id 
    @GeneratedValue 
    private int id;

    @Column(unique = true, nullable = false, length = 30)
    private String username;
    @Column(nullable = false, length = 30)
    private String password;

    @OneToMany
    private List<Survey> ownedSurveys;

    public Account() {
        username = "";
        password = "";
    }

    /* Getters */
    
    public int getId() {
        return id;
    }
    
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    /* Setters */

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
