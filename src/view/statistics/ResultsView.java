package view.statistics;

import entity.AnsweredTest;
import entity.Test;
import entity.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import logic.StatisticsLogic;
import service.AnsweredTestService;
import service.TestService;
import service.UserService;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-23.
 */
public class ResultsView {

    User currUser;
    FlowPane flowpane = new FlowPane();
    Pane pane;
    Pane contentPane;

    List<AnsweredTest> tests;
    ComboBox testBox;

    Label testTitle;
    Label points;
    Label time;
    Label grade;


    public ResultsView(Stage window) {
        currUser = UserService.read(LoginLogic.getCurrId());

        pane = new Pane();
        pane.getStyleClass().add("BackGroundPane");

        contentPane = new Pane();
        contentPane.setPrefSize(500,400);
        contentPane.relocate(550,250);
        contentPane.getStyleClass().add("ContentPane");
        pane.getChildren().add(contentPane);

        MenuBarHelper.getMenuBar(window, pane);

        tests = AnsweredTestService.readIfResultShared(LoginLogic.getCurrId());

        List<String> testNames = new ArrayList<>();

        for(AnsweredTest element : tests) {
            testNames.add(element.getTest().gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );


        testBox = new ComboBox(availableTests);
        testBox.relocate(130,70);

        contentPane.getChildren().addAll(testBox);

        Scene scene = new Scene(pane, 1600, 900);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/TestViewCSS.css").toExternalForm());
        window.setScene(scene);
        window.show();

        // When a test is selected in testBox
        testBox.setOnAction(e->{
            showTestResults();
        });
    }

    public void showTestResults() {
        pane.getChildren().removeAll(testTitle, points, time, grade);

        // Loads selected AnsweredTest from db
        int selectedTestIndex = testBox.getSelectionModel().getSelectedIndex();
        AnsweredTest selectedTest = tests.get(selectedTestIndex);

        // Sets all labels
        testTitle = new Label("Valt prov: " + selectedTest.getTest().gettTitle());
        points = new Label("Poäng: " + selectedTest.getaTPoints() + " / "
            + selectedTest.getTest().gettMaxPoints());
        int timeSec = selectedTest.getaTTimeSec();
        int timeMin = timeSec / 60;
        int leftOverSec = timeSec % 60;
        time = new Label("Tidåtgång: " + timeMin + " min "
                + leftOverSec + " sek / " + selectedTest.getTest().gettTimeMin() + " min");
        grade = new Label("Betyg: " + selectedTest.getaTGrade());

        testTitle.relocate(130,120);
        points.relocate(130, 170);
        time.relocate(130, 220);
        grade.relocate(130, 270);

        contentPane.getChildren().addAll(testTitle, points, time, grade);
    }


}
