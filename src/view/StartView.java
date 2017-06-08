package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartView extends Application{
    Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setResizable(false);
        LoginPage startView = new LoginPage(window);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
