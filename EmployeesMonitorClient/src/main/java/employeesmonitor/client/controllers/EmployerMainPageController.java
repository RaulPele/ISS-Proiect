package employeesmonitor.client.controllers;

import employeesmonitor.client.Main;
import employeesmonitor.client.ScreenManager;
import employeesmonitor.model.Employer;
import employeesmonitor.services.IController;
import employeesmonitor.services.IObserver;
import employeesmonitor.services.ServiceException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

public class EmployerMainPageController implements IObserver {
    private ScreenManager screenManager;
    private IController server;
    private Employer employer;

    private EmployerEmployeesPageController employeesPageController;
    private TasksPageController tasksPageController;

    @FXML
    private GridPane frameGridPane;

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void setServer(IController server) {
        this.server = server;
    }

    public void initializePage(Employer employer) {
        this.employer = employer;
    }

    public void onEmployeesButtonClicked(ActionEvent actionEvent) {
        changeToEmployeesPage();
    }

    private void changeToEmployeesPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/employer_employees_page-view.fxml"));

        try {
            frameGridPane.getChildren().clear();
            frameGridPane.getChildren().add(fxmlLoader.load());
            employeesPageController =  fxmlLoader.getController();
            employeesPageController.setScreenManager(screenManager);
            employeesPageController.setServer(server);
            employeesPageController.initializePage(employer);

            tasksPageController = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTasksButtonClicked(ActionEvent actionEvent) {
        changeToTasksPage();
    }

    private void changeToTasksPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/tasks_page-view.fxml"));
        try{
            frameGridPane.getChildren().clear();
            frameGridPane.getChildren().add(fxmlLoader.load());
            tasksPageController = fxmlLoader.getController();
            tasksPageController.setScreenManager(screenManager);
            tasksPageController.setServer(server);
            tasksPageController.initializePage(employer);

            employeesPageController = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLogoutButtonClicked(ActionEvent actionEvent) {
        changeToLoginScreen();
    }

    private void changeToLoginScreen() {

        try{
            frameGridPane.getChildren().clear();
            screenManager.changeToLoginPage();
            screenManager.getLoginController().setScreenManager(screenManager);
            screenManager.getLoginController().setServer(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskAdded(){
        Platform.runLater(() -> {
            if (tasksPageController != null) {
                tasksPageController.reloadTasks();
            }
        });
    }

    @Override
    public void onTaskAssigned() throws ServiceException {
        Platform.runLater(() -> {
            if (tasksPageController != null) {
                tasksPageController.reloadTasks();
            }
        });
    }

    @Override
    public void onTaskRemoved() throws ServiceException {
        Platform.runLater(() -> {
            if (tasksPageController != null ) {
                tasksPageController.reloadTasks();
            }
        });
    }

    @Override
    public void onEmployeeRemoved() throws ServiceException {
        Platform.runLater(() -> {
            if (employeesPageController != null ) {
                employeesPageController.reloadData();
            }
        });
    }
}
