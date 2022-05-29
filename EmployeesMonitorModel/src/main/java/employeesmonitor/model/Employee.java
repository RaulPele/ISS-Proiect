package employeesmonitor.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Employee extends CompanyMember implements Serializable {
    private LocalDateTime startedWorkdayAt;
    private LocalDateTime finishedWorkdayAt;
    private Employer employer;
    private AuthCredentials authCredentials;
    private String email;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String gender) {
        super(firstName, lastName, gender);
    }

    public LocalDateTime getStartedWorkdayAt() {
        return startedWorkdayAt;
    }

    public void setStartedWorkdayAt(LocalDateTime startedWorkdayAt) {
        this.startedWorkdayAt = startedWorkdayAt;
    }

    public LocalDateTime getFinishedWorkdayAt() {
        return finishedWorkdayAt;
    }

    public void setFinishedWorkdayAt(LocalDateTime finishedWorkdayAt) {
        this.finishedWorkdayAt = finishedWorkdayAt;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
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
