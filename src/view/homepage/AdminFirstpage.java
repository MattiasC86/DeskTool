package view.homepage;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        BorderPane bp = new BorderPane();
        bp.setTop(pane);
        bp.setId("firstpagePane");


        Label l1 = new Label("Välkommen till Newtons Provportal");
        l1.setStyle("-fx-font-size: 24pt");
        l1.relocate(100,100);
        pane.getChildren().add(l1);

        Label l2 = new Label("Prov i databasen");
        l2.setStyle("-fx-font-size: 24pt");
        l2.relocate(700,50);
        pane.getChildren().add(l2);

        TableView table = new TableView();
        table.relocate(700, 100);
        table.setPrefSize(800, 700);
        table.setStyle("-fx-font-size: 12pt");
        pane.getChildren().add(table);


        TableColumn testCol = new TableColumn("Prov");
        testCol.setPrefWidth(200);

        TableColumn timeCol = new TableColumn("Tidsgräns");
        timeCol.setPrefWidth(200);

        TableColumn questionsCol = new TableColumn("Antal fråor");
        questionsCol.setPrefWidth(200);

        TableColumn createdCol = new TableColumn("Lärare");
        createdCol.setPrefWidth(200);


        table.getColumns().addAll(testCol, timeCol, questionsCol, createdCol);




        Scene welcomeScene = new Scene(bp, 1600,900);
        welcomeScene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(welcomeScene);
        window.centerOnScreen();



    }
}