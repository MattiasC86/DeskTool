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

    List<AnsweredTest> tests;
    ComboBox testBox;

    Label testTitle;
    Label points;
    Label time;
    Label grade;


    public ResultsView(Stage window) {
        currUser = UserService.read(LoginLogic.getCurrId());

        Pane menuPane = new Pane();

        Pane mainPane = new Pane();

        pane = new Pane();
        pane.relocate(200,100);

        MenuBarHelper.getMenuBar(window, menuPane);

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
        testBox.relocate(100,100);

        pane.getChildren().addAll(testBox);

        mainPane.getChildren().addAll(menuPane, pane);

        Scene scene = new Scene(mainPane, 1600, 900);
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

        testTitle.relocate(100,150);
        points.relocate(100, 200);
        time.relocate(100, 250);
        grade.relocate(100, 300);

        pane.getChildren().addAll(testTitle, points, time, grade);
    }


}
