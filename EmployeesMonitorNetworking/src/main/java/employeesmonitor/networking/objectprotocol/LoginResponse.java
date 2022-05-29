package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.CompanyMember;

public class LoginResponse implements Response {
    private CompanyMember loggedMember;

    public LoginResponse(CompanyMember loggedMember) {
        this.loggedMember = loggedMember;
    }

    public CompanyMember getLoggedMember() {
        return loggedMember;
    }
}
