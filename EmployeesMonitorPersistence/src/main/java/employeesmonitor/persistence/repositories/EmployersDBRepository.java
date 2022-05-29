package employeesmonitor.persistence.repositories;

import employeesmonitor.model.Employer;

public interface EmployersDBRepository extends Repository<Long, Employer> {
    Employer findEmployerByEmail(String email);
}
