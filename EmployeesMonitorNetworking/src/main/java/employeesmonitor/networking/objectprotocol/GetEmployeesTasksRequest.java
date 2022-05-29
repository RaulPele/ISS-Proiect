package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employee;

public class GetEmployeesTasksRequest implements Request {
    private Employee employee;

    public GetEmployeesTasksRequest(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
