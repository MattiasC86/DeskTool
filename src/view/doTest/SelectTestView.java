package view.doTest;

import entity.Question;
import entity.Test;
import entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.UserLogic;
import service.AnswerService;
import service.QuestionService;
import service.UserService;
import view.menuBars.MenuBarAdmin;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-18.
 */
public class SelectTestView {
    ComboBox testBox;
    List<Test> tests;
    int testBoxIndex;
    Test selectedTest;
    List<Question> testQuestions;
    List<List> testAnswers = new ArrayList<>();

    public SelectTestView(Stage window) {

        window.setTitle("Välj test");

        Pane pane = new Pane();

        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        //------GETTING TEST, QUESTIONS AND ANSWERS FROM DB---------
        User user = UserService.read(1);
        tests = UserLogic.getAvailableTests(user);
        List<String> testTitles = new ArrayList<>();
        for(Test element : tests) {
            testTitles.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testTitles
                );
        testBox = new ComboBox(availableTests);

        Label chooseTest = new Label("Välj prov:");
        Label testName = new Label("Prov:");
        Label teacherName = new Label("Lärare:");
        Label timeLimit = new Label("Tidsgräns:");
        Label totalQuestion = new Label("Antal frågor:");
        Label maxPoints = new Label("Max poäng:");

        Label currentTestName = new Label("");
        Label currentTeacherName = new Label("");
        Label currentTimeLimit = new Label("");
        Label currentTotalQuestion = new Label("");
        Label currentMaxPoints = new Label("");

        Button startTest = new Button("Stata prov");


        chooseTest.relocate(400,50);
        testBox.relocate(500,50);

        testName.relocate(400,100);
        currentTestName.relocate(500,100);

        teacherName.relocate(400, 150);
        currentTeacherName.relocate(500,150);

        timeLimit.relocate(400, 200);
        currentTimeLimit.relocate(500,200);

        totalQuestion.relocate(400, 250);
        currentTotalQuestion.relocate(500,250);

        maxPoints.relocate(400, 300);
        currentMaxPoints.relocate(500,300);

        startTest.relocate(400,350);

        pane.getChildren().addAll(chooseTest,testBox,testName,teacherName,timeLimit,totalQuestion,maxPoints,startTest);
        pane.getChildren().addAll(currentTestName, currentTeacherName, currentTimeLimit, currentTotalQuestion, currentMaxPoints);


        window.setOnCloseRequest(e -> Platform.exit());
        Scene scene = new Scene(pane, 1600, 900);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(scene);
        window.show();

        startTest.setOnAction(e->{
            //ActionEvent för startknappen!
        });

        testBox.setOnAction(e->{
            loadData();
            currentTestName.setText(selectedTest.gettTitle());
            currentTeacherName.setText(selectedTest.getUser().getFirstName() + " " + selectedTest.getUser().getLastName());
            currentTimeLimit.setText(Integer.toString(selectedTest.gettTimeMin()));
            currentTotalQuestion.setText(Integer.toString(testQuestions.size()));
            currentMaxPoints.setText(Integer.toString(selectedTest.gettMaxPoints()));
        });


    }

    public void loadData() {
        testBoxIndex = testBox.getSelectionModel().getSelectedIndex();
        // Chosen Test is saved as selectedTest
        selectedTest = tests.get(testBoxIndex);

        // Questions are saved to testQuestions List
        testQuestions = QuestionService.read(selectedTest.getTestId());

        // testAnswers[0] will contain a List with all answers for 1st Question, testAnswers[1] all answers for 2nd Question etc.
        testAnswers = new ArrayList<>();
        for(Question q : testQuestions) {
            testAnswers.add(AnswerService.read(q.getQuestionId()));
        }
    }


}
