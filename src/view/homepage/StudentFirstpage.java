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
import javafx.stage.Stage;
import logic.LoginLogic;
import logic.UserLogic;
import service.TestService;
import service.UserService;
import view.menuBars.MenuBarAdmin;
import view.menuBars.MenuBarStudent;

import java.util.List;

/**
 * Created by Rasmus on 2017-05-16.
 */
public class StudentFirstpage {

    List<Test> testList;

    public StudentFirstpage(Stage window){

        window.setTitle("Hem");

        Pane pane = new Pane();

        MenuBarStudent x = new MenuBarStudent(pane, window);
        BorderPane bp = new BorderPane();
        bp.setTop(pane);
        bp.setId("firstpagePane");

        Label l1 = new Label("Välkommen till Newtons Provportal");
        l1.setStyle("-fx-font-size: 24pt");
        l1.setTextFill(Color.WHITE);
        l1.relocate(100,100);
        pane.getChildren().add(l1);

        Label l2 = new Label("Till höger ser du prov du inte har gjort ännu");
        l2.setStyle("-fx-font-size: 18pt");
        l2.setTextFill(Color.WHITE);
        l2.relocate(100,160);
        pane.getChildren().add(l2);

        Label l3 = new Label("För att göra prov gå in på Arkiv -> Test -> Gör test");
        l3.setStyle("-fx-font-size: 18pt");
        l3.setTextFill(Color.WHITE);
        l3.relocate(100,190);
        pane.getChildren().add(l3);

        Label l4 = new Label("Prov du har kvar att göra");
        l4.setStyle("-fx-font-size: 24pt");
        l4.setTextFill(Color.WHITE);
        l4.relocate(700,50);
        pane.getChildren().add(l4);

        testList = UserLogic.getAvailableTests(UserService.read(LoginLogic.getCurrId()));

        TableView table = new TableView();
        table.relocate(700, 100);
        table.setPrefSize(800, 700);
        table.setStyle("-fx-font-size: 12pt");
        pane.getChildren().add(table);
        table.setItems(getTests());

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


        table.getColumns().addAll(testName, testTime, testQuestions, teacher);





        Scene welcomeScene = new Scene(bp, 1600,900);
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
