package employeesmonitor.client.controllers;

import employeesmonitor.model.Employer;
import employeesmonitor.model.TaskStatus;
import employeesmonitor.services.IController;
import employeesmonitor.services.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddTaskController {
    private IController server;
    private Employer employer;

    @FXML
    private TextField nameTextField;

    @FXML
    private DatePicker deadlineDayPicker;

    @FXML
    private TextField deadlineTimeTextField;

    @FXML
    private TextArea descriptionTextArea;

    public void setServer(IController server) {
        this.server = server;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void onAddButtonClicked(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        LocalDate deadlineDay = deadlineDayPicker.getValue();
        String timeString = deadlineTimeTextField.getText();
        LocalTime deadlineTime = LocalTime.parse(timeString);
        LocalDateTime deadline = LocalDateTime.of(deadlineDay, deadlineTime);
        String description = descriptionTextArea.getText();

        try {
            server.addTask(name, deadline, description, TaskStatus.UNASSIGNED, employer);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }

        clearInputs();
    }

    private void clearInputs() {
        nameTextField.clear();
        descriptionTextArea.clear();
        deadlineTimeTextField.clear();
    }
}
