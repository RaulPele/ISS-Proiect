package employeesmonitor.server;

import employeesmonitor.model.AuthCredentials;
import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.persistence.repositories.EmployeesDBRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeesService {
    private EmployeesDBRepository employeesRepository;

    public EmployeesService(EmployeesDBRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public void addEmployee(String firstName, String lastName, String email, String password, String gender, Employer employer) {
        Employee employee = new Employee(firstName, lastName, gender);
        AuthCredentials authCredentials = new AuthCredentials(email, password);

        employee.setEmail(email);
        employee.setAuthCredentials(authCredentials);
        employee.setEmployer(employer);

        employeesRepository.save(employee);
    }

    public List<Employee> getAllEmployees(Employer employer) {
        List<Employee> employees = new ArrayList<>();
        employeesRepository.getAllEmployees(employer).forEach(employees::add);

        return employees;
    }

    public void removeEmployee(Employee employee) {
        employeesRepository.delete(employee);
    }
}
