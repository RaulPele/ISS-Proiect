package employeesmonitor.persistence.repositories;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;

public interface EmployeesDBRepository extends Repository<Long, Employee> {
    Employee findEmployeeByEmail(String email);
    Iterable<Employee> getAllEmployees(Employer employer);
}
