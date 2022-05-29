package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Task;

public class AssignTaskRequest implements Request {
    private Task task;
    private Employee employee;

    public AssignTaskRequest(Task task, Employee employee) {
        this.task = task;
        this.employee = employee;
    }

    public Task getTask() {
        return task;
    }

    public Employee getEmployee() {
        return employee;
    }
}
