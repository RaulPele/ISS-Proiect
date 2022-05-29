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
import java.util.List;

public class ClientWorker implements IObserver, Runnable {
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private boolean connected;
    private IController server;

    public ClientWorker(Socket connection, IController server) {
        this.connection = connection;
        this.server = server;

        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();

            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if (response != null){
                    sendResponse((Response) response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    private Response handleRequest(Request request) {
        if (request instanceof LoginRequest loginRequest) {

            try {
                CompanyMember member = server.login(loginRequest.getEmail(), loginRequest.getPassword());
                server.addObserverClient(member.getID(), this);

                return new LoginResponse(member);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof  AddEmployeeRequest addEmployeeRequest) {
            try{
                String firstName = addEmployeeRequest.getFirstName();
                String lastName = addEmployeeRequest.getLastName();
                String email = addEmployeeRequest.getEmail();
                String gender = addEmployeeRequest.getGender();
                String password = addEmployeeRequest.getPassword();
                Employer employer = addEmployeeRequest.getEmployer();

                server.addEmployee(firstName, lastName, email, password, gender, employer);
                return new OkResponse();
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof  GetAllEmployeesRequest getAllEmployeesRequest) {
            try{
                Employer employer = getAllEmployeesRequest.getEmployer();
                List<Employee> employees = server.getAllEmployees(employer);
                return new GetAllEmployeesResponse(employees);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof GetEmployerByEmailRequest getEmployerByEmailRequest) {
            try {
                String email = getEmployerByEmailRequest.getEmail();
                return new GetEmployerByEmailResponse(server.findEmployerByEmail(email));
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof  AddTaskRequest addTaskRequest) {
            try {
                String name = addTaskRequest.getName();
                LocalDateTime deadline = addTaskRequest.getDeadline();
                String description = addTaskRequest.getDescription();
                TaskStatus status = addTaskRequest.getStatus();
                Employer creator = addTaskRequest.getTaskCreator();

                server.addTask(name, deadline, description, status, creator);

                return new OkResponse();
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof GetAllTasksRequest getAllTasksRequest) {
            try{
                Employer tasksCreator = getAllTasksRequest.getTasksCreator();
                List<Task> tasks = server.getAllTasks(tasksCreator);
                return new GetAllTasksResponse(tasks);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof AssignTaskRequest assignTaskRequest) {
            try {
                Task task = assignTaskRequest.getTask();
                Employee employee = assignTaskRequest.getEmployee();
                server.assignTask(task, employee);
                return new OkResponse();
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof GetEmployeesTasksRequest getEmployeesTasksRequest) {
            try {
                Employee employee = getEmployeesTasksRequest.getEmployee();
                List<Task> tasks = server.getEmployeesTasks(employee);
                return new GetEmployeesTaskResponse(tasks);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof  RemoveTaskRequest removeTaskRequest) {
            try {
                Task task = removeTaskRequest.getTask();
                server.removeTask(task);
                return new OkResponse();
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }

        } else if (request instanceof RemoveEmployeeRequest removeEmployeeRequest) {
            try {
                Employee employee = removeEmployeeRequest.getEmployee();
                server.removeEmployee(employee);
                return new OkResponse();
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        return null;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("Sending response: " +  response);

        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public synchronized void onTaskAdded() throws ServiceException {
        try{
            sendResponse(new TaskAddedResponse());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public synchronized void onTaskAssigned() throws ServiceException {
        try {
            sendResponse(new TaskAssignedResponse());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public synchronized void onTaskRemoved() throws ServiceException {
        try {
            sendResponse(new TaskRemovedResponse());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void onEmployeeRemoved() throws ServiceException {
        try {
            sendResponse(new EmployeeRemovedResponse());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
