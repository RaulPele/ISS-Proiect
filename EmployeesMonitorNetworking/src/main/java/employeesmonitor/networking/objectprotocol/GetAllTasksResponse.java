package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Task;

import java.util.List;

public class GetAllTasksResponse implements Response {
    private List<Task> tasks;

    public GetAllTasksResponse(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
