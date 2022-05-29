package employeesmonitor.networking.objectprotocol;

public class GetEmployerByEmailRequest implements Request {
    private String email;

    public GetEmployerByEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
