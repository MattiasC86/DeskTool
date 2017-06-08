package view;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Rasmus on 2017-05-03.
 */

public class StartView extends Application{
    Stage window;

    //Start class with main method
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
