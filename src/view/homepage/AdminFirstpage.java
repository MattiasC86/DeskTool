package view.homepage;

import entity.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.TestService;
import view.menuBars.MenuBarAdmin;
import javafx.scene.paint.Color;

import javax.persistence.TemporalType;

import java.util.List;

import static java.awt.Color.*;


/**
 * Created by Rasmus on 2017-05-03.
 */
public class AdminFirstpage {

    List<Test> testList;

    //Firstpage for admin
    public AdminFirstpage(Stage window) {

        window.setTitle("Hem");

        Pane menuPane = new Pane();

        MenuBarAdmin x = new MenuBarAdmin(menuPane, window);

        Pane mainPane = new Pane();

        Pane textPane = new Pane();
        textPane.relocate(100,100);
        textPane.setPrefHeight(700);
        textPane.setPrefWidth(500);

        Label l1 = new Label("Välkommen!");
        l1.setStyle("-fx-font-size: 24pt");
        l1.relocate(50, 20);
        textPane.getChildren().add(l1);

        Label l2 = new Label("Prov i databasen");
        l2.setStyle("-fx-font-size: 24pt");
        l2.setTextFill(Color.WHITE);
        l2.relocate(700, 50);
        l2.setTextFill(Color.WHITE);
        mainPane.getChildren().add(l2);

        // testList will contain all Tests from database
        testList = TestService.readAll();


        TableColumn<Table, String> testName = new TableColumn<>("Prov");
        testName.setMinWidth(200);
        testName.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Table, Integer> testTime = new TableColumn<>("Tidsgräns / min");
        testTime.setMinWidth(200);
        testTime.setCellValueFactory(new PropertyValueFactory<>("timeLimit"));

        TableColumn<Table, Integer> testQuestions = new TableColumn<>("Antal frågor");
        testQuestions.setMinWidth(200);
        testQuestions.setCellValueFactory(new PropertyValueFactory<>("questions"));

        TableColumn<Table, Integer> teacher = new TableColumn<>("Lärare");
        teacher.setMinWidth(200);
        teacher.setCellValueFactory(new PropertyValueFactory<>("user"));

        testName.setStyle( "-fx-alignment: CENTER;");
        testTime.setStyle( "-fx-alignment: CENTER;");
        testQuestions.setStyle( "-fx-alignment: CENTER;");
        teacher.setStyle( "-fx-alignment: CENTER;");


        TableView<Table> table = new TableView();
        table.setItems(getTests());
        table.getColumns().addAll(testName, testTime, testQuestions, teacher) ;

        table.relocate(700, 100);
        table.setPrefSize(800, 700);
        table.setStyle("-fx-font-size: 12pt");

        mainPane.getChildren().addAll(menuPane, table, textPane);

        mainPane.setId("firstpagePane");
        textPane.setId("textPane");
        l1.getStyleClass().add("firstpageL1");
        l2.getStyleClass().add("firstpageL1");


        Scene welcomeScene = new Scene(mainPane, 1600, 900);
        welcomeScene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(welcomeScene);
        window.centerOnScreen();



    }


    public ObservableList<Table> getTests(){
        ObservableList<Table> tests = FXCollections.observableArrayList();
        for(int i = 0; i < testList.size(); i++) {
            tests.addAll(new Table(testList.get(i).gettTitle(), testList.get(i).gettTimeMin(), testList.get(i).gettMaxPoints(),
                    testList.get(i).getUser().getFirstName() + " " + testList.get(i).getUser().getLastName()));
        }
            return tests;
    }
}