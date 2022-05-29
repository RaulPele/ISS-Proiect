package employeesmonitor.server;

import employeesmonitor.model.*;
import employeesmonitor.services.AuthenticationException;
import employeesmonitor.services.IController;
import employeesmonitor.services.IObserver;
import employeesmonitor.services.ServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements IController {
    private Authentication authentication;
    private EmployeesService employeesService;
    private EmployersService employersService;
    private TasksService tasksService;

    public Controller(Authentication authentication, EmployeesService employeesService, EmployersService employersService, TasksService tasksService) {
        this.authentication = authentication;
        this.employeesService = employeesService;
        this.employersService = employersService;
        this.tasksService = tasksService;
    }

    @Override
    public CompanyMember login(String email, String password) throws ServiceException {
         try{
             return authentication.login(email, password);
         } catch (AuthenticationException ex) {
             throw new ServiceException(ex.getMessage());
         }
    }

    @Override
    public void addEmployee(String firstName, String lastName, String email, String password, String gender, Employer employer) throws ServiceException {
        employeesService.addEmployee(firstName, lastName, email, password, gender, employer);
    }

    @Override
    public List<Employee> getAllEmployees(Employer employer) {
        return employeesService.getAllEmployees(employer);
    }

    @Override
    public Employer findEmployerByEmail(String email) throws ServiceException {
        return employersService.findEmployerByEmail(email);
    }

    @Override
    public void addTask(String name, LocalDateTime deadline, String description, TaskStatus status, Employer taskCreator) {
        tasksService.addTask(name, deadline, description, status, taskCreator);

        notifyClientsOnTaskAdded();
    }

    @Override
    public void addObserverClient(Long clientID, IObserver client) {
        authentication.addObserverClient(clientID, client);
    }

    @Override
    public List<Task> getAllTasks(Employer taskCreator) throws ServiceException {
        return tasksService.getAllTasks(taskCreator);
    }

    @Override
    public void assignTask(Task task, Employee employee) throws ServiceException {
        tasksService.assignTask(task, employee);
        notifyClientsOnTaskAssigned();
    }

    @Override
    public List<Task> getEmployeesTasks(Employee employee) throws ServiceException {
        return tasksService.getEmployeesTasks(employee);
    }

    @Override
    public void removeTask(Task task) throws ServiceException {
        tasksService.removeTask(task);
        notifyClientsOnTaskRemoved();
    }

    @Override
    public void removeEmployee(Employee employee) throws ServiceException {
        tasksService.unassignAllTasks(employee);
        employeesService.removeEmployee(employee);
        notifyClientsOnEmployeeRemoved();
    }

    private void notifyClientsOnTaskAdded() {
        Map<Long, IObserver> loggedClients = authentication.getLoggedClients();
        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(IObserver client: loggedClients.values()){
            executor.execute(() -> {
                try {
                    client.onTaskAdded();
                } catch (ServiceException e) {
                    System.err.println("Error notifying " + e);
                }
            });
        }

        executor.shutdown();
    }

    private void notifyClientsOnTaskAssigned() {
        Map<Long, IObserver> loggedClients = authentication.getLoggedClients();
        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(IObserver client: loggedClients.values()){
            executor.execute(() -> {
                try {
                    client.onTaskAssigned();
                } catch (ServiceException e) {
                    System.err.println("Error notifying" + e);
                }
            });
        }

        executor.shutdown();
    }

    private void notifyClientsOnTaskRemoved() {
        Map<Long, IObserver> loggedClients = authentication.getLoggedClients();
        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(IObserver client: loggedClients.values()){
            executor.execute(() -> {
                try {
                    client.onTaskRemoved();
                } catch (ServiceException e) {
                    System.err.println("Error notifying " + e);
                }
            });
        }

        executor.shutdown();
    }

    private void notifyClientsOnEmployeeRemoved() {
        Map<Long, IObserver> loggedClients = authentication.getLoggedClients();
        ExecutorService executor= Executors.newFixedThreadPool(5);

        for(IObserver client: loggedClients.values()){
            executor.execute(() -> {
                try {
                    client.onEmployeeRemoved();
                } catch (ServiceException e) {
                    System.err.println("Error notifying " + e);
                }
            });
        }

        executor.shutdown();
    }
}
