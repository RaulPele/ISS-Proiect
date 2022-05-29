package employeesmonitor.model;

import java.io.Serializable;

public class AuthCredentials implements Serializable {
    private String email;
    private String password;

    public AuthCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthCredentials(){

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
