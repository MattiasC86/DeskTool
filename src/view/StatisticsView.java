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
import logic.StatisticsLogic;
import service.AnsweredTestService;
import service.TestService;
import service.UserService;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

import static view.doTest.SelectTestView.getSelectedTest;

/**
 * Created by matti on 2017-05-23.
 */
public class StatisticsView{
    User currUser;

    List<User> userList;
    List<Test> userTests;
    List<Test> testList;

    private ComboBox userBox;
    private ComboBox testBox;
    private ComboBox userTestBox;


    Label test;
    Label nrDone;
    Label nrPassed;
    Label avgScore;
    Label avgTime;

    Label status;
    Label student;
    Label uTest;
    Label grade;
    Label points;
    Label uTime;

    FlowPane pane;

    /*
    - Välj elev
        - Välj test
            - Godkänt
            - Poäng
            - Tidåtgång

    - Välj ett test
        - Antal genomförda test
        - Antal godkända test
        - Avarage score
        - Avarage score

     */

    public StatisticsView (Stage window) {
        currUser = UserService.read(LoginLogic.getCurrId());
        userList = UserService.readAll();

        Pane barpane = new Pane();

        MenuBarHelper.getMenuBar(window, barpane);

        pane = new FlowPane();
        barpane.getChildren().add(pane);
        pane.relocate(0,100);


        List<String> userNames = new ArrayList<>();

        for(User element : userList) {
            userNames.add(element.getUserName());
        }
        ObservableList<String> availableUsers =
                FXCollections.observableArrayList(
                        userNames
                );
        userBox = new ComboBox(availableUsers);

        // If logged in user is Admin, all tests are shown
        // If user is Teacher, only the tests created by this teacher is shown
        switch (currUser.getRole()) {
            case "Admin":
                testList = TestService.readAll();
                break;
            case "Teacher":
                testList = TestService.readAll(currUser.getUserId());
                break;
        }

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

        Scene scene = new Scene(barpane, 1600, 900);
        window.setScene(scene);
        window.show();

        // When a user is selected in userBox
        userBox.setOnAction(e->{
            loadUserTestBox();
        });

        // When a test is selected in testBox
        testBox.setOnAction(e->{
            showTestStatistics();
        });
    }

    public void loadUserTestBox(){
        // Loads selected user from db
        int selectedUserIndex = userBox.getSelectionModel().getSelectedIndex();
        User selectedUser = userList.get(selectedUserIndex);

        // Loads all tests shared with selected user from db
        userTests = TestService.readAllByStudent(selectedUser.getUserId());

        // Creates new ComboBox userTestBox to show all tests shared with selected user
        List<String> testNames = new ArrayList<>();
        for(Test element : userTests) {
            testNames.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );
        pane.getChildren().removeAll(userTestBox, test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);
        userTestBox = new ComboBox(availableTests);

        pane.getChildren().addAll(userTestBox);

        // When a test is selected for the user selected in userBox
        userTestBox.setOnAction(e-> {
            showUserTestStatistics(selectedUser);
        });
    }

    // Shows statistics for selected test
    public void showTestStatistics() {
        pane.getChildren().removeAll(userTestBox, test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);

        // Loads selected test from db
        int selectedTestIndex = testBox.getSelectionModel().getSelectedIndex();
        Test selectedTest = testList.get(selectedTestIndex);
        System.out.println("TEST ID: " + selectedTest.getTestId());

        test  = new Label("Prov: " + selectedTest.gettTitle());
        nrDone = new Label("Antal genomförda: " + StatisticsLogic.getNrDone(selectedTest.getTestId()) +
                " / " + StatisticsLogic.getNrAccess(selectedTest.getTestId()));
        pane.getChildren().addAll(test, nrDone);

        // If any student has done the test
        if(StatisticsLogic.getNrDone(selectedTest.getTestId()) != 0) {

            nrPassed = new Label("Antal godkända: " + StatisticsLogic.getNrPassed(selectedTest.getTestId()));
            avgScore = new Label("Snittpoäng: " + StatisticsLogic.getAvgScore(selectedTest.getTestId()) + " / "
                    + selectedTest.gettMaxPoints());
            int avgTimeSec = StatisticsLogic.getAvgTime(selectedTest.getTestId());
            int avgTimeMin = avgTimeSec / 60;
            int leftOverSec = avgTimeSec % 60;
            avgTime = new Label("Snittid: " + avgTimeMin + " min " +
                    leftOverSec + " sek / " + selectedTest.gettTimeMin() + " min");

            pane.getChildren().addAll(nrPassed, avgScore, avgTime);
        }
    }

    // Shows statistics for selected user and test
    public void showUserTestStatistics(User selectedUser) {
        pane.getChildren().removeAll(test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);

        // Loads selected test from db
        int selectedTestIndex = userTestBox.getSelectionModel().getSelectedIndex();
        Test selectedTest = userTests.get(selectedTestIndex);
        System.out.println("TEST ID: " + selectedTest.getTestId());

        // Loads the answered test data from db based on selectes user and test
        AnsweredTest at = AnsweredTestService.read(selectedUser.getUserId(), selectedTest.getTestId());
        System.out.println("AT ÄR: " + at);

        // If student havn't yet completed the test
        if(at == null) {
            status = new Label(selectedUser.getFirstName() + " " + selectedUser.getLastName() + " har ännu inte genomfört detta test");
            pane.getChildren().addAll(status);

        //If student has completed the test
        } else {
            student = new Label("Elev: " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
            uTest  = new Label("Prov: " + selectedTest.gettTitle());
            grade = new Label("Betyg: " + at.getaTGrade());
            points = new Label("Poäng:" + at.getaTPoints());
            uTime = new Label("Tidåtgång: " + (at.getaTTimeSec() / 60) + " minuter");

            pane.getChildren().addAll(student, uTest, grade, points, uTime);
        }

    }

    public void loadTestBox() {

    }



}
