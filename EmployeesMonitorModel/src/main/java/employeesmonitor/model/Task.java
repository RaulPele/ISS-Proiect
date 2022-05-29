package employeesmonitor.model;

import java.time.LocalDateTime;

public class Task extends Entity<Long> {
    private String name;
    private LocalDateTime deadline;
    private String description;
    private Employee assignedEmployee;
    private TaskStatus status;
    private Employer taskCreator;

    public Task(String name, LocalDateTime deadline, String description, Employer taskCreator) {
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.taskCreator = taskCreator;
        status = TaskStatus.UNASSIGNED;
    }

    public Task() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
            this.assignedEmployee = assignedEmployee;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Employer getTaskCreator() {
        return taskCreator;
    }

    public void setTaskCreator(Employer taskCreator) {
        this.taskCreator = taskCreator;
    }
}
