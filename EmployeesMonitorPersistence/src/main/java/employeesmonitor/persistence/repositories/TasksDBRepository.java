package employeesmonitor.persistence.repositories;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.model.Task;

public interface TasksDBRepository extends Repository<Long, Task> {
    Iterable<Task> getAllTasks(Employer tasksCreator);
    Iterable<Task> getEmployeesTasks(Employee employee);
    void unassignAllTasks(Employee employee);
}
