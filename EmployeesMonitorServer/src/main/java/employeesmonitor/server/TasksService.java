package employeesmonitor.server;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.model.Task;
import employeesmonitor.model.TaskStatus;
import employeesmonitor.persistence.repositories.TasksDBRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TasksService {
    private TasksDBRepository tasksRepository;

    public TasksService(TasksDBRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public void addTask(String name, LocalDateTime deadline, String description, TaskStatus status, Employer taskCreator) {
        Task task = new Task(name, deadline, description, taskCreator);
        task.setStatus(status);
        tasksRepository.save(task);
    }

    public List<Task> getAllTasks(Employer tasksCreator) {
        List<Task> tasks = new ArrayList<>();
        tasksRepository.getAllTasks(tasksCreator).forEach(tasks::add);

        return tasks;
    }

    public void assignTask(Task task, Employee employee) {
        if (task.getStatus() == TaskStatus.UNASSIGNED) {
            Task assignedTask = new Task(task.getName(), task.getDeadline(), task.getDescription(), task.getTaskCreator());
            assignedTask.setID(task.getID());
            assignedTask.setAssignedEmployee(employee);
            assignedTask.setStatus(TaskStatus.ASSIGNED);

            tasksRepository.update(assignedTask);
        }
    }

    public List<Task> getEmployeesTasks(Employee employee) {
        List<Task> tasks = new ArrayList<>();
        tasksRepository.getEmployeesTasks(employee).forEach(tasks::add);

        return tasks;
    }

    public void removeTask(Task task) {
        tasksRepository.delete(task);
    }

    public void unassignAllTasks(Employee employee) {
        tasksRepository.unassignAllTasks(employee);
    }
}
