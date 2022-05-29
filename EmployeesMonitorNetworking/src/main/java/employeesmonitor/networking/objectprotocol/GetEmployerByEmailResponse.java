package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employer;

public class GetEmployerByEmailResponse implements Response {
    private Employer employer;

    public GetEmployerByEmailResponse(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return employer;
    }
}
