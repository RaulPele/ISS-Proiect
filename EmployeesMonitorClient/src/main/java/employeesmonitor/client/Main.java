package employeesmonitor.client;

import employeesmonitor.client.controllers.LoginController;
import employeesmonitor.networking.objectprotocol.ClientProxy;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static int DEFAULT_PORT = 55556;
    private static String DEFAULT_HOST = "localhost";

    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager screenManager = new ScreenManager(stage);
        screenManager.changeToLoginPage();

        LoginController loginController = screenManager.getLoginController();
        loginController.setScreenManager(screenManager);
        loginController.setServer(new ClientProxy(DEFAULT_HOST, DEFAULT_PORT));

        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}