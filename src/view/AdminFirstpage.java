package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by Rasmus on 2017-05-03.
 */
public class AdminFirstpage {

    public AdminFirstpage(Stage window){

        Pane pane = new Pane();

        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        Scene welcomeScene = new Scene(pane, 1280,720);
        window.setScene(welcomeScene);
        window.centerOnScreen();



    }
}