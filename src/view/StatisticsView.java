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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import service.AnsweredTestService;
import service.TestService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

import static view.doTest.SelectTestView.getSelectedTest;

/**
 * Created by matti on 2017-05-23.
 */
public class StatisticsView extends Application{
    List<User> userList;
    List<Test> userTests;

    private ComboBox userBox;
    private ComboBox testBox;
    private ComboBox userTestBox;
    FlowPane pane = new FlowPane();

    /*
    - Välj elev
        - Välj test
            - Godkänt
            - Poäng

    - Välj ett test
        - Antal genomförda test
        - Antal godkända test
        - Avarage score

     */

    public void start(Stage primaryStage) {
        userList = UserService.readAll();

        List<String> userNames = new ArrayList<>();

        for(User element : userList) {
            userNames.add(element.getUserName());
        }
        ObservableList<String> availableUsers =
                FXCollections.observableArrayList(
                        userNames
                );
        userBox = new ComboBox(availableUsers);

        List<Test> testList = TestService.readAll(1); //LoginLogic.getCurrId()

        List<String> testNames = new ArrayList<>();
        for(Test element : testList) {
            testNames.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );
        testBox = new ComboBox(availableTests);

        pane.getChildren().addAll(userBox, testBox);


        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        userBox.setOnAction(e->{
            loadUserTestBox();
        });

        userTestBox.setOnAction(e-> {
            showUserTestStatistics();
        });
    }

    public void loadUserTestBox(){
        int selectedUserIndex = userBox.getSelectionModel().getSelectedIndex();
        User selectedUser = userList.get(selectedUserIndex);

        userTests = TestService.readAllByStudent(selectedUser.getUserId());

        List<String> testNames = new ArrayList<>();
        for(Test element : userTests) {
            testNames.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );
        userTestBox = new ComboBox(availableTests);
        pane.getChildren().remove(userTestBox);
        pane.getChildren().addAll(userTestBox);
    }

    public void showUserTestStatistics() {

        //If elev har genomfört test, visa följande
        int selectedUserIndex = userBox.getSelectionModel().getSelectedIndex();
        User selectedUser = userList.get(selectedUserIndex);

        int selectedTestIndex = userTestBox.getSelectionModel().getSelectedIndex();
        Test selectedTest = userTests.get(selectedUserIndex);

        AnsweredTest at = AnsweredTestService.read(selectedUser.getUserId(), selectedTest.getTestId());

        Label grade = new Label("Betyg: " + at.getaTGrade());
        Label points = new Label("Poäng:");
        Label time = new Label("Tidåtgång:");

        Label currentTimeLimit = new Label("");
        Label currentTotalQuestion = new Label("");
        Label currentMaxPoints = new Label("");

        pane.getChildren().addAll(grade);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loadTestBox() {

    }



}
