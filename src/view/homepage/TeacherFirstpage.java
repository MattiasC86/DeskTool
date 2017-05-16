package view.homepage;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.menuBars.MenuBarTeacher;

/**
 * Created by Rasmus on 2017-05-16.
 */
public class TeacherFirstpage {

    public TeacherFirstpage(Stage window){

        window.setTitle("Hem");

        Pane pane = new Pane();

        MenuBarTeacher x = new MenuBarTeacher(pane, window);

        BorderPane bp = new BorderPane();

        bp.setTop(pane);

        bp.setId("firstpagePane");

        Scene welcomeScene = new Scene(bp, 1600,900);
        welcomeScene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(welcomeScene);
        window.centerOnScreen();



    }
}
