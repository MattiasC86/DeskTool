package view.homepage;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.menuBars.MenuBarAdmin;
import view.menuBars.MenuBarStudent;

/**
 * Created by Rasmus on 2017-05-16.
 */
public class StudentFirstpage {

    public StudentFirstpage(Stage window){

        window.setTitle("Hem");

        Pane pane = new Pane();

        MenuBarStudent x = new MenuBarStudent(pane, window);

        BorderPane bp = new BorderPane();

        bp.setTop(pane);

        bp.setId("firstpagePane");

        Scene welcomeScene = new Scene(bp, 1600,900);
        welcomeScene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(welcomeScene);
        window.centerOnScreen();



    }
}
