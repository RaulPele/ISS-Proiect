package employeesmonitor.client.controllers;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.model.Task;
import employeesmonitor.services.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class EmployerAssignTaskController {
    private IController server;
    private Employer employer;
    private Task selectedTask;

    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    @FXML
    private TableView<Employee> employeesTableView;

    @FXML
    private TableColumn<Employee, Long> badgeNumberColumn;

    @FXML
    private TableColumn<Employee, String> firstNameColumn;

    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    @FXML
    private TableColumn<Employee, LocalDateTime> startedWorkingColumn;

    @FXML
    private TableColumn<Employee, String> genderColumn;

    public void setServer(IController server) {
        this.server = server;
    }

    public void initializePage(Employer employer, Task selectedTask) {
        this.employer = employer;
        this.selectedTask = selectedTask;
        loadEmployees();
        displayEmployees();
    }

    private void loadEmployees() {
        try {
            employees.setAll(server.getAllEmployees(employer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayEmployees() {
        badgeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("badgeNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        employeesTableView.setItems(employees);
    }

    public void onAssignButtonClicked(ActionEvent actionEvent) {
        Employee selectedEmployee = employeesTableView.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                server.assignTask(selectedTask, selectedEmployee);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
