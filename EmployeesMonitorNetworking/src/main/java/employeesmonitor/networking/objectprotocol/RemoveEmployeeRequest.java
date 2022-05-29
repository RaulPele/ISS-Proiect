package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employee;

public class RemoveEmployeeRequest implements Request {
    private Employee employee;

    public RemoveEmployeeRequest(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
