package employeesmonitor.client.controllers;

import employeesmonitor.client.ScreenManager;
import employeesmonitor.model.CompanyMember;
import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.services.IController;
import employeesmonitor.services.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    private ScreenManager screenManager;
    private IController server;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailTextField;


    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void setServer(IController server) {
        this.server = server;
    }

    public void onLoginButtonClicked(ActionEvent actionEvent) {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || email.isBlank() || password.isEmpty() || password.isBlank()) {
            return;
        }

        try {
            CompanyMember loggedMember = server.login(email, password);

            if (loggedMember instanceof Employer) {
                initializeEmployerMainPageController();
                server.addObserverClient(loggedMember.getID(), screenManager.getEmployerMainPageController());

                changeToEmployerMainPage(email);
                screenManager.getEmployerMainPageController().initializePage((Employer) loggedMember);

            } else if (loggedMember instanceof Employee) {
                initializeEmployeeMainPageController();
                server.addObserverClient(loggedMember.getID(), screenManager.getEmployeeMainPageController());

                changeToEmployeeMainPage();
                screenManager.getEmployeeMainPageController().initializePage((Employee) loggedMember);
            }

        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    private void initializeEmployerMainPageController() {
        EmployerMainPageController mainPageController = screenManager.getEmployerMainPageController();

        if(mainPageController == null){
            mainPageController = new EmployerMainPageController();
            mainPageController.setServer(server);
            mainPageController.setScreenManager(screenManager);
            screenManager.setEmployerMainPageController(mainPageController);
        }
        System.out.println(mainPageController);
    }

    private void initializeEmployeeMainPageController() {
        EmployeeMainPageController mainPageController = screenManager.getEmployeeMainPageController();

        if(mainPageController == null){
            mainPageController = new EmployeeMainPageController();
            mainPageController.setServer(server);
            mainPageController.setScreenManager(screenManager);
            screenManager.setEmployeeMainPageController(mainPageController);
        }
    }

    private void changeToEmployerMainPage(String email) {
        try {
            screenManager.changeToEmployerMainPage();
            EmployerMainPageController mainPageController = screenManager.getEmployerMainPageController();
//            mainPageController.setScreenManager(screenManager);
//            mainPageController.setServer(server);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToEmployeeMainPage() {
        try{
            screenManager.changeToEmployeeMainPage();
            EmployeeMainPageController mainPageController = screenManager.getEmployeeMainPageController();
//            mainPageController.setScreenManager(screenManager);
//            mainPageController.setServer(server);
//            mainPageController.initializePage(server.findEmployeeByEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
