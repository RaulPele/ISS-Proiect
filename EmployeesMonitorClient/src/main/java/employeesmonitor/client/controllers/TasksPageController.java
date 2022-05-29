package employeesmonitor.client.controllers;

import employeesmonitor.client.ScreenManager;
import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.model.Task;
import employeesmonitor.model.TaskStatus;
import employeesmonitor.services.IController;
import employeesmonitor.services.ServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;

public class TasksPageController {
    private ScreenManager screenManager;
    private IController server;
    private Employer employer;
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

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void initializePage(Employer employer) {
        this.employer = employer;
        loadTasks();
        displayTasks();
    }

    private void loadTasks() {
        try{
            tasks.setAll(server.getAllTasks(employer));
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

    public void onAddTaskButtonClicked(ActionEvent actionEvent) {
        try{
            screenManager.openAddTaskForm();
            AddTaskController addTaskController = screenManager.getAddTaskController();
            addTaskController.setServer(server);
            addTaskController.setEmployer(employer);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAssignTaskButtonClicked(ActionEvent actionEvent) {
        try{
            Task selectedTask = tasksTableView.getSelectionModel().getSelectedItem();

            if (selectedTask != null && selectedTask.getStatus() == TaskStatus.UNASSIGNED) {
                screenManager.openAssignTaskPage();
                EmployerAssignTaskController assignTaskController = screenManager.getEmployerAssignTaskController();
                assignTaskController.setServer(server);
                assignTaskController.initializePage(employer, selectedTask);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadTasks() {
        loadTasks();
        displayTasks();
    }

    public void onRemoveTaskButtonClicked(ActionEvent actionEvent) {
        try {
            Task selectedTask = tasksTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                server.removeTask(selectedTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
