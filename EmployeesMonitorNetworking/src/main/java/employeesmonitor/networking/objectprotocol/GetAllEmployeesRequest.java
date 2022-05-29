package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employer;

public class GetAllEmployeesRequest implements Request {
    private Employer employer;

    public GetAllEmployeesRequest(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return employer;
    }
}
