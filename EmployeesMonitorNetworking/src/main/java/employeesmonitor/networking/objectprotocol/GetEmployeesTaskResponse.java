package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Task;

import java.util.List;

public class GetEmployeesTaskResponse implements Response {
    private List<Task> tasks;

    public GetEmployeesTaskResponse(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
