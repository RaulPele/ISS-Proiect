package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.*;
import employeesmonitor.services.IController;
import employeesmonitor.services.IObserver;
import employeesmonitor.services.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientProxy implements IController {
    private IObserver client;
    private Socket connection;

    private String host;
    private int port;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private BlockingQueue<Response> responses;
    private boolean finished;

    public ClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();

            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished = true;

        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompanyMember login(String email, String password) throws ServiceException {
        initializeConnection();

        LoginRequest request = new LoginRequest(email, password);
        sendRequest(request);

        Response response = readResponse();

        if (response instanceof LoginResponse loginResponse) {
//            this.client = client;
            return loginResponse.getLoggedMember();
        } else if (response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }

        return null;
    }

    @Override
    public void addEmployee(String firstName, String lastName, String email, String password, String gender, Employer employer) throws ServiceException {
        AddEmployeeRequest request = new AddEmployeeRequest(firstName, lastName, email, password, gender, employer);
        sendRequest(request);

        Response response = readResponse();

        if (response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees(Employer employer) throws ServiceException {
        GetAllEmployeesRequest request = new GetAllEmployeesRequest(employer);
        sendRequest(request);

        Response response = readResponse();
        List<Employee> employees = new ArrayList<>();
        if (response instanceof GetAllEmployeesResponse getAllEmployeesResponse) {
            employees = getAllEmployeesResponse.getEmployees();
        }

        return employees;
    }

    @Override
    public Employer findEmployerByEmail(String email) throws ServiceException {
        GetEmployerByEmailRequest request = new GetEmployerByEmailRequest(email);
        sendRequest(request);

        Response response = readResponse();
        Employer employer = null;

        if (response instanceof GetEmployerByEmailResponse getEmployerByEmailResponse) {
            employer = getEmployerByEmailResponse.getEmployer();
        }

        return employer;
    }

    @Override
    public void addTask(String name, LocalDateTime deadline, String description, TaskStatus status, Employer taskCreator) throws ServiceException {
        AddTaskRequest request = new AddTaskRequest(name, deadline, description, status, taskCreator);
        sendRequest(request);

        Response response = readResponse();
        if (response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }
    }

    @Override
    public void addObserverClient(Long clientID, IObserver client) throws ServiceException{
        this.client = client;
    }

    @Override
    public List<Task> getAllTasks(Employer taskCreator) throws ServiceException {
        GetAllTasksRequest request = new GetAllTasksRequest(taskCreator);
        sendRequest(request);

        Response response = readResponse();

        if (response instanceof GetAllTasksResponse getAllTasksResponse) {
            return getAllTasksResponse.getTasks();
        } else if (response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }

        return null;
    }

    @Override
    public void assignTask(Task task, Employee employee) throws ServiceException {
        AssignTaskRequest request = new AssignTaskRequest(task, employee);
        sendRequest(request);

        Response response = readResponse();

        if (response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }
    }

    @Override
    public List<Task> getEmployeesTasks(Employee employee) throws ServiceException {
        GetEmployeesTasksRequest request = new GetEmployeesTasksRequest(employee);
        sendRequest(request);

        Response response = readResponse();

        if (response instanceof GetEmployeesTaskResponse getEmployeesTaskResponse) {
            return getEmployeesTaskResponse.getTasks();
        } else if(response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }

        return null;
    }

    @Override
    public void removeTask(Task task) throws ServiceException {
        RemoveTaskRequest request = new RemoveTaskRequest(task);
        sendRequest(request);

        Response response = readResponse();

        if (response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }
    }

    @Override
    public void removeEmployee(Employee employee) throws ServiceException {
        RemoveEmployeeRequest request = new RemoveEmployeeRequest(employee);
        sendRequest(request);

        Response response = readResponse();

        if (response instanceof ErrorResponse errorResponse) {
            System.out.println(errorResponse.getMessage());
        }
    }

    private void sendRequest(Request request) throws ServiceException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ServiceException("Error sending object: " + e);
        }
    }

    private Response readResponse() throws ServiceException {
        Response response = null;

        try {
            response=responses.take();
        } catch (InterruptedException e) {
            throw new ServiceException("Error reading response: " + e);
        }
        return response;
    }

    private void startReader(){
        Thread readerThread = new Thread(new ReaderThread());
        readerThread.start();
    }

    private void handleUpdate(UpdateResponse response) {
        if(response instanceof TaskAddedResponse) {
            try {
                client.onTaskAdded();
            } catch (ServiceException e) {
                e.printStackTrace();
            }

        } else if (response instanceof TaskAssignedResponse) {
            try {
                client.onTaskAssigned();
            } catch (ServiceException e) {
                e.printStackTrace();
            }

        } else if (response instanceof  TaskRemovedResponse) {
            try {
                client.onTaskRemoved();
            } catch (ServiceException e) {
                e.printStackTrace();
            }

        } else if (response instanceof EmployeeRemovedResponse) {
            try{
                client.onEmployeeRemoved();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable {

        @Override
        public void run() {
            while(!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("Response received: " + response);
                    if(response instanceof  UpdateResponse){
                        handleUpdate((UpdateResponse) response);
                    }else {
                        responses.put((Response) response);
                    }
//                    responses.put((Response) response);

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
