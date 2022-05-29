package employeesmonitor.client.controllers;

import employeesmonitor.model.Employer;
import employeesmonitor.services.IController;
import employeesmonitor.services.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

public class AddEmployeeController {
    private IController server;
    private Employer employer;
    private EmployerEmployeesPageController parent;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField genderTextField;

    public void setServer(IController server) {
        this.server = server;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void setParent(EmployerEmployeesPageController parent) {
        this.parent = parent;
    }

    public void onAddEmployeeButtonClicked(ActionEvent actionEvent) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String gender = genderTextField.getText();

        try{
            server.addEmployee(firstName, lastName, email, password, gender, employer);
            clearInputs();
            parent.reloadData();

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void clearInputs() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        passwordField.clear();
        genderTextField.clear();
    }

}
