package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;

public class AddEmployeeRequest implements Request {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private Employer employer;

    public AddEmployeeRequest(String firstName, String lastName, String email, String password, String gender, Employer employer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.employer = employer;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public Employer getEmployer() {
        return employer;
    }
}
