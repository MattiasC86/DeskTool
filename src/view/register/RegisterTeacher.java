package view.register;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.MenuBarAdmin;

/**
 * Created by Rasmus on 2017-05-09.
 */
public class RegisterTeacher {

    public RegisterTeacher(Stage window){

        Pane pane = new Pane();

       // MenuBarAdmin x = new MenuBarAdmin(pane, window);

        RegisterForm rf = new RegisterForm(pane, window);


        Scene welcomeScene = new Scene(pane, 1600,900);
        window.setScene(welcomeScene);
        window.centerOnScreen();



    }
}
