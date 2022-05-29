package employeesmonitor.model;

import java.io.Serializable;

public class Employer extends CompanyMember implements Serializable {
    private AuthCredentials authCredentials;
    private String email;

    public Employer() {

    }

    public Employer(String firstName, String lastName, String gender) {
        super(firstName, lastName, gender);
    }

    public AuthCredentials getAuthCredentials() {
        return authCredentials;
    }

    public void setAuthCredentials(AuthCredentials authCredentials) {
        this.authCredentials = authCredentials;
        this.email = authCredentials.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
