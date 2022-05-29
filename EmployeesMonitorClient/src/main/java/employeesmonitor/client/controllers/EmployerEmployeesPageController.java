package employeesmonitor.client.controllers;

import employeesmonitor.client.ScreenManager;
import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.services.IController;
import employeesmonitor.services.IObserver;
import employeesmonitor.services.ServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class EmployerEmployeesPageController {
    private ScreenManager screenManager;
    private Employer employer;
    private IController server;
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Employee, Long> badgeNumberColumn;

    @FXML
    private TableColumn<Employee, String> firstNameColumn;


    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    @FXML
    private TableColumn<Employee, String> startedWorkingColumn;

    @FXML
    private TableColumn<Employee, String> genderColumn;

    @FXML
    private TableView<Employee> employeesTableView;

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void setServer(IController server) {
        this.server = server;
    }

    public void initializePage(Employer employer){
        this.employer = employer;

        loadEmployees();
        displayEmployees();
    }

    private void loadEmployees() {
        try {
            employees.setAll(server.getAllEmployees(employer));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void displayEmployees() {
        badgeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("badgeNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        startedWorkingColumn.setCellValueFactory(new PropertyValueFactory<>("startedWorkdayAt"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        employeesTableView.setItems(employees);
    }

    public void reloadData() {
        loadEmployees();
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void onAddEmployeeButtonClicked(ActionEvent actionEvent) {
        try{
            screenManager.openAddEmployeeForm();
            AddEmployeeController addEmployeeController = screenManager.getAddEmployeeController();
            addEmployeeController.setServer(server);
            addEmployeeController.setEmployer(employer);
            addEmployeeController.setParent(this);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onViewTasksButtonClicked(ActionEvent actionEvent) {
        try{
            screenManager.openEmployeeTasksPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRemoveEmployeeButtonClicked(ActionEvent actionEvent) {
        try {
            Employee selectedEmployee = employeesTableView.getSelectionModel().getSelectedItem();

            if (selectedEmployee!= null ) {
                server.removeEmployee(selectedEmployee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
