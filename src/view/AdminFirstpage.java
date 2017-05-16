package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.menuBars.MenuBarAdmin;


/**
 * Created by Rasmus on 2017-05-03.
 */
public class AdminFirstpage {

    public AdminFirstpage(Stage window){

        window.setTitle("Hem");

        Pane pane = new Pane();

        Label l1 = new Label("Välkommen till Newtons Provportal");
        l1.setStyle("-fx-font-size: 24pt");
        l1.relocate(100,100);

        pane.getChildren().add(l1);



        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        BorderPane bp = new BorderPane();

        bp.setTop(pane);

        bp.setId("firstpagePane");

        Scene welcomeScene = new Scene(bp, 1600,900);
        welcomeScene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(welcomeScene);
        window.centerOnScreen();
    }
}