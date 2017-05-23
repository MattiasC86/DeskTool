package view;

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
import javafx.stage.Stage;
import logic.StatisticsLogic;
import service.TestService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-23.
 */
public class ResultsView extends Application {

    User currUser;
    FlowPane pane = new FlowPane();

    List<AnsweredTest> tests;
    ComboBox testBox;

    Label testTitle;
    Label points;
    Label time;
    Label grade;

    @Override
    public void start(Stage primaryStage) throws Exception {
        currUser = UserService.read(1); //LoginLogic.getCurrId();

        tests = StatisticsLogic.getCorrected(1);

        List<String> testNames = new ArrayList<>();

        for(AnsweredTest element : tests) {
            testNames.add(element.getTest().gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );
        testBox = new ComboBox(availableTests);

        pane.getChildren().addAll(testBox);

        Scene scene = new Scene(pane, 1600, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

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

        pane.getChildren().addAll(testTitle, points, time, grade);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
