package view.statistics;

import entity.AnsweredTest;
import entity.StudentGroup;
import entity.Test;
import entity.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import logic.StatisticsLogic;
import service.*;
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
    List<StudentGroup> groupList;
    List<Test> groupTests;

    private ComboBox userBox;
    private ComboBox testBox;
    private ComboBox userTestBox;
    private ComboBox groupBox;
    private ComboBox groupTestBox;

    Label test;
    Label gTest;
    Label nrDone;
    Label gNrDone;
    Label nrPassed;
    Label gNrPassed;
    Label avgScore;
    Label gAvgScore;
    Label avgTime;
    Label gAvgTime;
    Label gGroup;

    Label status;
    Label student;
    Label uTest;
    Label grade;
    Label points;
    Label uTime;

    Pane studentpane;
    Pane testpane;

    FlowPane pane;

    public StatisticsView (Stage window) {
        currUser = UserService.read(LoginLogic.getCurrId());
        userList = UserService.readAll();
        groupList = StudentGroupService.readAll();

        Pane mainpane = new Pane();

        Pane barpane = new Pane();

        //Pane tabpane = new Pane();


        MenuBarHelper.getMenuBar(window, barpane);


        TabPane tp = new TabPane();
        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab student = new Tab("Student");
        studentpane = new Pane();
        studentpane.setPrefWidth(1200);
        studentpane.setPrefHeight(600);

        Tab test = new Tab("Prov");
        testpane = new Pane();
        testpane.setPrefWidth(1200);
        testpane.setPrefHeight(600);

        tp.getTabs().addAll(student, test);


        tp.relocate(200,100);
        mainpane.getChildren().addAll(barpane,tp);

        userTestBox = new ComboBox();
        userTestBox.setPrefWidth(200);

        List<String> userNames = new ArrayList<>();
        for(User element : userList) {
            userNames.add(element.getUserName());
        }
        ObservableList<String> availableUsers =
                FXCollections.observableArrayList(
                        userNames
                );
        userBox = new ComboBox(availableUsers);
        userBox.setPrefWidth(200);

        List<String> groupNames = new ArrayList<>();
        for(StudentGroup element : groupList) {
            groupNames.add(element.getGroupName());
        }
        ObservableList<String> availableGroups =
                FXCollections.observableArrayList(
                        groupNames
                );
        groupBox = new ComboBox(availableGroups);
        groupBox.setPrefWidth(200);


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
        testBox.setPrefWidth(200);



        userTestBox.relocate(350, 100);
        userBox.relocate(100,100);
        studentpane.getChildren().addAll(userBox, userTestBox);
        student.setContent(studentpane);

        testBox.relocate(100,100);
        testpane.getChildren().addAll(testBox);
        test.setContent(testpane);

        Scene scene = new Scene(mainpane, 1600, 900);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(scene);
        window.show();

        //////////////////////////////////////////////////////////////////////////////

        pane = new FlowPane();
        //barpane.getChildren().add(pane);
        pane.relocate(0,100);


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
        studentpane.getChildren().removeAll(userTestBox, test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);
        userTestBox = new ComboBox(availableTests);
        userTestBox.setPrefWidth(200);

        studentpane.getChildren().addAll(userTestBox);
        userTestBox.relocate(350,100);

        // When a test is selected for the user selected in userBox
        userTestBox.setOnAction(e-> {
            showUserTestStatistics(selectedUser);
        });
    }

    public void loadGroupTestBox(){
        // Loads selected StudentGroup from db
        int selectedGroupIndex = groupBox.getSelectionModel().getSelectedIndex();
        StudentGroup selectedGroup = groupList.get(selectedGroupIndex);

        // Loads all tests
        groupTests = TestService.readAll();

        // Creates new ComboBox userTestBox to show all tests
        List<String> testNames = new ArrayList<>();
        for(Test element : groupTests) {
            testNames.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );
        studentpane.getChildren().removeAll(userTestBox, test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);
        groupTestBox = new ComboBox(availableTests);
        groupTestBox.setPrefWidth(200);

        studentpane.getChildren().addAll(userTestBox);
        groupTestBox.relocate(350,100);

        // When a test is selected for the group
        groupTestBox.setOnAction(e-> {
            showGroupTestStatistics(selectedGroup);
        });
    }

    // Shows statistics for selected test
    public void showTestStatistics() {
        testpane.getChildren().removeAll(userTestBox, test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);

        // Loads selected test from db
        int selectedTestIndex = testBox.getSelectionModel().getSelectedIndex();
        Test selectedTest = testList.get(selectedTestIndex);
        System.out.println("TEST ID: " + selectedTest.getTestId());

        test  = new Label("Prov: " + selectedTest.gettTitle());
        nrDone = new Label("Antal genomförda: " + StatisticsLogic.getNrDone(selectedTest.getTestId()) +
                " / " + StatisticsLogic.getNrAccess(selectedTest.getTestId()));

        test.relocate(100, 150);
        nrDone.relocate(100, 200);

        testpane.getChildren().addAll(test, nrDone);

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

            nrPassed.relocate(100, 250);
            avgScore.relocate(100, 300);
            avgTime.relocate(100, 350);


            testpane.getChildren().addAll(nrPassed, avgScore, avgTime);
        }
    }

    // Shows statistics for selected user and test
    public void showUserTestStatistics(User selectedUser) {
        studentpane.getChildren().removeAll(test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);

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
            status.relocate(100, 150);
            studentpane.getChildren().addAll(status);

        //If student has completed the test
        } else {

            student = new Label("Elev: " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
            uTest  = new Label("Prov: " + selectedTest.gettTitle());
            grade = new Label("Betyg: " + at.getaTGrade());
            points = new Label("Poäng:" + at.getaTPoints());
            uTime = new Label("Tidåtgång: " + (at.getaTTimeSec() / 60) + " minuter");

            student.relocate(100,150);
            uTest.relocate(100, 200);
            grade.relocate(100, 250);
            points.relocate(100, 300);
            uTime.relocate(100, 350);

            studentpane.getChildren().addAll(student, uTest, grade, points, uTime);
        }

    }

    public void showGroupTestStatistics(StudentGroup selectedStudentGroup) {
        studentpane.getChildren().removeAll(test, nrDone, nrPassed, avgScore, avgTime, status, student, uTest, grade, points, uTime);

        // Loads selected test from db
        int selectedTestIndex = groupTestBox.getSelectionModel().getSelectedIndex();
        Test selectedTest = groupTests.get(selectedTestIndex);

        // Stores all members in StudentGroup
        List<User> allMembers = StudentGroupService.readByGroup(selectedStudentGroup.getStudentGroupId());
        // Stores all AnsweredTests for the test taken by members
        List<AnsweredTest> allAnsweredTests = AnsweredTestService.read(allMembers, selectedTest.getTestId());
        // These variables store how many members had access to test and how many have taken the test so far
        int membersAccessToTest = TestAccessService.read(allMembers, selectedTest.getTestId());
        int membersDoneTest = 0;
            for(AnsweredTest at : allAnsweredTests) {
                membersDoneTest++;
            }

        gTest  = new Label("Prov: " + selectedTest.gettTitle());
        gGroup = new Label("Vald grupp: " + selectedStudentGroup.getGroupName());
        gNrDone = new Label("Antal genomförda: " + membersDoneTest + " / " + membersAccessToTest);
        gNrPassed = new Label("Antal godkända: " + StatisticsLogic.getNrPassed(allMembers, selectedTest.getTestId()));
        gAvgScore = new Label("Snittpoäng: " + StatisticsLogic.getAvgScore(allMembers, selectedTest.getTestId()) + " / "
                + selectedTest.gettMaxPoints());
        int avgTimeSec = StatisticsLogic.getAvgTime(allMembers, selectedTest.getTestId());
        int avgTimeMin = avgTimeSec / 60;
        int leftOverSec = avgTimeSec % 60;
        gAvgTime = new Label("Snittid: " + avgTimeMin + " min " +
                leftOverSec + " sek / " + selectedTest.gettTimeMin() + " min");
    }

    public void loadTestBox() {

    }



}
