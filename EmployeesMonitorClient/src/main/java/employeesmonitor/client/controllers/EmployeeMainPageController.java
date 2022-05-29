package employeesmonitor.client.controllers;

import employeesmonitor.client.ScreenManager;
import employeesmonitor.model.Employee;
import employeesmonitor.model.Task;
import employeesmonitor.services.IController;
import employeesmonitor.services.IObserver;
import employeesmonitor.services.ServiceException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class EmployeeMainPageController implements IObserver{
    private ScreenManager screenManager;
    private IController server;
    private Employee employee;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private TableView<Task> tasksTableView;

    @FXML
    private TableColumn<Task, String> nameColumn;

    @FXML
    private TableColumn<Task, LocalDateTime> deadlineColumn;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    public void setServer(IController server) {
        this.server = server;
    }

    public void initializePage(Employee employee) {
        this.employee = employee;
        loadTasks();
        displayTasks();
    }

    private void loadTasks() {
        try{
            tasks.setAll(server.getEmployeesTasks(employee));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayTasks() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tasksTableView.setItems(tasks);
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void onTaskAdded() throws ServiceException {

    }

    @Override
    public void onTaskAssigned() throws ServiceException {
        Platform.runLater(() -> {
            loadTasks();
            displayTasks();
        });
    }

    @Override
    public void onTaskRemoved() throws ServiceException {
        Platform.runLater(() -> {
            loadTasks();
            displayTasks();
        });
    }

    @Override
    public void onEmployeeRemoved() throws ServiceException {

    }
}
