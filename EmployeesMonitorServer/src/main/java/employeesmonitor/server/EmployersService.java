package employeesmonitor.server;

import employeesmonitor.model.Employer;
import employeesmonitor.persistence.repositories.EmployersDBRepository;

public class EmployersService {
    private EmployersDBRepository employersRepository;

    public EmployersService(EmployersDBRepository employersRepository) {
        this.employersRepository = employersRepository;
    }

    public Employer findEmployerByEmail(String email) {
        return employersRepository.findEmployerByEmail(email);
    }
}
