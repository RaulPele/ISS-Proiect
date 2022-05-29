package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Task;

public class RemoveTaskRequest implements Request {
    private Task task;

    public RemoveTaskRequest(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
