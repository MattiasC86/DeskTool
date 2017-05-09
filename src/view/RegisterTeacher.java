package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Rasmus on 2017-05-06.
 */
public class RegisterTeacher {
    /*
    Förnamn
    Efternamn
    Användarnamn
    Email
    Lösenord
     */
    public RegisterTeacher(Stage window) {

        Pane pane = new Pane();

        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        Scene welcomeScene = new Scene(pane, 1280,720);
        window.setScene(welcomeScene);
        window.centerOnScreen();

    }
}

