package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employer;
import employeesmonitor.model.TaskStatus;

import java.time.LocalDateTime;

public class AddTaskRequest implements Request {
    private String name;
    private LocalDateTime deadline;
    private String description;
    private TaskStatus status;
    private Employer taskCreator;

    public AddTaskRequest(String name, LocalDateTime deadline, String description, TaskStatus status, Employer taskCreator) {
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.taskCreator = taskCreator;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public Employer getTaskCreator() {
        return taskCreator;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
