package employeesmonitor.server;

import employeesmonitor.model.CompanyMember;
import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.persistence.repositories.EmployeesDBRepository;
import employeesmonitor.persistence.repositories.EmployersDBRepository;
import employeesmonitor.services.AuthenticationException;
import employeesmonitor.services.IObserver;

import java.util.HashMap;
import java.util.Map;

public class Authentication {
    private Map<Long, IObserver> loggedClients;
    private EmployeesDBRepository employeesRepository;
    private EmployersDBRepository employersRepository;

    public Authentication(EmployeesDBRepository employeesRepository, EmployersDBRepository employersRepository) {
        this.loggedClients = new HashMap<>();
        this.employeesRepository = employeesRepository;
        this.employersRepository = employersRepository;
    }

    public Map<Long, IObserver> getLoggedClients() {
        return loggedClients;
    }

    public CompanyMember login(String email, String password) throws AuthenticationException {
        if (isEmployerEmail(email)) {
            Employer employer =  employersRepository.findEmployerByEmail(email); //verify password

            return employer;
        } else if (isEmployeeEmail(email)) {
            Employee employee =  employeesRepository.findEmployeeByEmail(email); //verify password

            return employee;
        }
        return null;
    }

    public void addObserverClient(Long clientID, IObserver client) {
        this.loggedClients.put(clientID, client);
    }


    private boolean isEmployerEmail(String email) {
        return email.contains("@employer.com");
    }

    private boolean isEmployeeEmail(String email) {
        return email.contains("@employee.com");
    }
}
