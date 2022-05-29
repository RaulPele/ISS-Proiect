package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employee;

import java.util.List;

public class GetAllEmployeesResponse implements Response {
    private List<Employee> employees;

    public GetAllEmployeesResponse(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
