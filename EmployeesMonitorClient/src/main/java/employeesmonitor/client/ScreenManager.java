package employeesmonitor.client;

import employeesmonitor.client.controllers.*;
import employeesmonitor.services.IObserver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScreenManager {
    private Stage window;
    private List<IObserver> activeWindows;

    private EmployerMainPageController employerMainPageController;
    private EmployeeMainPageController employeeMainPageController;
    private LoginController loginController;
    private AddEmployeeController addEmployeeController;
    private AddTaskController addTaskController;
    private EmployerAssignTaskController employerAssignTaskController;

    public ScreenManager(Stage window) {
        this.window = window;
        activeWindows = new ArrayList<>();
    }

    public List<IObserver> getActiveWindows() {
        return activeWindows;
    }

    public EmployerMainPageController getEmployerMainPageController() {
        return employerMainPageController;
    }

    public EmployerAssignTaskController getEmployerAssignTaskController() {
        return employerAssignTaskController;
    }

    public void setEmployerMainPageController(EmployerMainPageController employerMainPageController) {
        this.employerMainPageController = employerMainPageController;
    }

    public AddEmployeeController getAddEmployeeController() {
        return addEmployeeController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AddTaskController getAddTaskController() {
        return addTaskController;
    }

    public void setEmployeeMainPageController(EmployeeMainPageController employeeMainPageController) {
        this.employeeMainPageController = employeeMainPageController;
    }

    public EmployeeMainPageController getEmployeeMainPageController(){
        return employeeMainPageController;
    }

    public void changeToEmployerMainPage() throws  IOException{
        window.setScene(initializeEmployerMainPageScene());

//        if (!activeWindows.contains(employerMainPageController)) {
//            activeWindows.add(employerMainPageController);
//        }
    }

    private Scene initializeEmployerMainPageScene() throws  IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/employer_main_page-view.fxml"));
        Scene scene;

        if(employerMainPageController == null) {
            scene = new Scene(fxmlLoader.load());
            employerMainPageController = fxmlLoader.getController();
        } else {
            fxmlLoader.setControllerFactory((e) -> employerMainPageController);
            scene = new Scene(fxmlLoader.load());
            System.out.println((EmployerMainPageController) fxmlLoader.getController());
        }

        window.setTitle("Employees monitor");

        return scene;

    }

    public void changeToEmployeeMainPage() throws IOException {
        window.setScene(initializeEmployeeMainPageScene());

//        if (!activeWindows.contains(employeeMainPageController)) {
//            activeWindows.add(employeeMainPageController);
//        }
    }

    public Scene initializeEmployeeMainPageScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/employee_main_page-view.fxml"));
        Scene scene;

        if(employeeMainPageController == null) {
            scene = new Scene(fxmlLoader.load());
            employeeMainPageController = fxmlLoader.getController();
        } else {
            fxmlLoader.setControllerFactory((e) -> employeeMainPageController);
            scene = new Scene(fxmlLoader.load());
        }

        window.setTitle("Employees monitor");

        return scene;
    }

    public void changeToLoginPage() throws  IOException {
        window.setScene(initializeLoginPageScene());
    }

    private Scene initializeLoginPageScene() throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        loginController = fxmlLoader.getController();
        window.setTitle("Employees monitor");

        return scene;
    }

    public void openAddEmployeeForm() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/add_employee-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        addEmployeeController = fxmlLoader.getController();

        stage.setTitle("Add employee");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void openEmployeeTasksPage() throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/employee_tasks_page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Employee's tasks");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void openAddTaskForm() throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/add_task-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        addTaskController = fxmlLoader.getController();

        stage.setTitle("Add task");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void openAssignTaskPage() throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/employer_assign_task-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        employerAssignTaskController = fxmlLoader.getController();

        stage.setTitle("Assign tasks");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
