package employeesmonitor.services;

import employeesmonitor.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IController {
    CompanyMember login(String email, String password) throws ServiceException;
    void addEmployee(String firstName, String lastName, String email, String password, String gender, Employer employer) throws ServiceException;
    List<Employee> getAllEmployees(Employer employer) throws ServiceException;
    Employer findEmployerByEmail(String email) throws ServiceException;
    void addTask(String name, LocalDateTime deadline, String description, TaskStatus status, Employer taskCreator) throws ServiceException;
    void addObserverClient(Long clientID, IObserver client) throws ServiceException;
    List<Task> getAllTasks(Employer taskCreator) throws ServiceException;
    void assignTask(Task task, Employee employee) throws ServiceException;
    List<Task> getEmployeesTasks(Employee employee) throws ServiceException;
    void removeTask(Task task) throws ServiceException;
    void removeEmployee(Employee employee) throws ServiceException;
}
