package view.doTest;

import entity.Answer;
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
import logic.LoginLogic;
import logic.UserLogic;
import service.AnswerService;
import service.QuestionService;
import service.TestService;
import service.UserService;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-18.
 */
public class SelectTestView {
    User user;

    private ComboBox testBox;
    private List<Test> tests;
    private int testBoxIndex;

    //These variables have getters and will hold the selected test, questions and answers
    static private Test selectedTest;
    static public List<Question> testQuestions;
    static public List<List> testAnswers;
    static public List<doTestQuestion> doTestQuestionsList;

    public SelectTestView(Stage window) {

        window.setTitle("Välj test");

        Pane pane = new Pane();
        pane.getStyleClass().add("BackGroundPane");


        Pane contentPane = new Pane();
        contentPane.setPrefSize(400,500);
        contentPane.relocate(600,200);
        contentPane.getStyleClass().add("ContentPane");
        pane.getChildren().add(contentPane);



        user = UserService.read(LoginLogic.getCurrId());

        // If user is Admin, show all tests. If user is Student, show tests available to this Student
        tests = new ArrayList<>();
        switch(user.getRole()) {
            case "Admin":
                tests = TestService.readAll();
                break;
            case "Student":
                tests = UserLogic.getAvailableTests(user);
                break;
        }

        MenuBarHelper.getMenuBar(window, pane);

        // Fills testBox with all tests available to user
        List<String> testTitles = new ArrayList<>();
        for(Test element : tests) {
            testTitles.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testTitles
                );
        testBox = new ComboBox(availableTests);
        testBox.setMinWidth(200);

        Label chooseTest = new Label("Välj prov:");
        Label testName = new Label("Valt prov:");
        Label teacherName = new Label("Lärare:");
        Label timeLimit = new Label("Tidsgräns:");
        Label totalQuestion = new Label("Antal frågor:");
        Label maxPoints = new Label("Max poäng:");

        Label currentTestName = new Label("");
        Label currentTeacherName = new Label("");
        Label currentTimeLimit = new Label("");
        Label currentTotalQuestion = new Label("");
        Label currentMaxPoints = new Label("");

        //Sätt textstorlek
        chooseTest.setStyle("-fx-font-size: 12pt");
        testName.setStyle("-fx-font-size: 12pt");
        teacherName.setStyle("-fx-font-size: 12pt");
        timeLimit.setStyle("-fx-font-size: 12pt");
        totalQuestion.setStyle("-fx-font-size: 12pt");
        maxPoints.setStyle("-fx-font-size: 12pt");
        currentTestName.setStyle("-fx-font-size: 12pt");
        currentTeacherName.setStyle("-fx-font-size: 12pt");
        currentTimeLimit.setStyle("-fx-font-size: 12pt");
        currentTotalQuestion.setStyle("-fx-font-size: 12pt");
        currentMaxPoints.setStyle("-fx-font-size: 12pt");


        Button startTest = new Button("Starta prov");

        chooseTest.relocate(20,50);
        testBox.relocate(120,50);

        testName.relocate(20,100);
        currentTestName.relocate(140,100);

        teacherName.relocate(20, 150);
        currentTeacherName.relocate(140,150);

        timeLimit.relocate(20, 200);
        currentTimeLimit.relocate(140,200);

        totalQuestion.relocate(20, 250);
        currentTotalQuestion.relocate(140,250);

        maxPoints.relocate(20, 300);
        currentMaxPoints.relocate(140,300);

        startTest.relocate(120,350);
        startTest.setPrefSize(160, 50);
        startTest.setStyle("-fx-font-size: 16pt");

        contentPane.getChildren().addAll(chooseTest,testBox,testName,teacherName,timeLimit,totalQuestion,maxPoints,startTest);
        contentPane.getChildren().addAll(currentTestName, currentTeacherName, currentTimeLimit, currentTotalQuestion, currentMaxPoints);

        window.setOnCloseRequest(e -> Platform.exit());
        Scene scene = new Scene(pane, 1600, 900);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/TestViewCSS.css").toExternalForm());
        window.setScene(scene);
        window.show();

        // Redirects user to DoTestFXView
        startTest.setOnAction(e->{
            DoTestFxView dtfv = new DoTestFxView(window);
            dtfv.setTestInfo(selectedTest.gettTitle(), testQuestions.size(), selectedTest.gettTimeMin());

            doTestQuestionsList = new ArrayList();

            for(int i = 0; i < testQuestions.size(); i++) {

                String qType = testQuestions.get(i).getqType();

                List<Answer> list = testAnswers.get(i);



                if (qType.equalsIgnoreCase("Single")) {
                    doTestQuestion q = new doTestQuestion(list.size(), testQuestions.get(i).getqText(), list, dtfv.testList);
                    q.singleQuestion();
                    doTestQuestionsList.add(q);
                }
                else if (qType.equalsIgnoreCase("Multiple")) {
                    doTestQuestion q = new doTestQuestion(list.size(), testQuestions.get(i).getqText(), list, dtfv.testList);
                    q.manyQuestion();
                    doTestQuestionsList.add(q);
                }
                else if (qType.equalsIgnoreCase("Ranked")) {
                    doTestQuestion q = new doTestQuestion(list.size(), testQuestions.get(i).getqText(), list, dtfv.testList);
                    q.rankedQuestion();
                    doTestQuestionsList.add(q);
                }
            }
        });

        // Shows all info on selected test
        testBox.setOnAction(e->{
            loadData();
            currentTestName.setText(getSelectedTest().gettTitle());
            currentTeacherName.setText(getSelectedTest().getUser().getFirstName() + " " + getSelectedTest().getUser().getLastName());
            currentTimeLimit.setText(Integer.toString(getSelectedTest().gettTimeMin()) + " minuter");
            currentTotalQuestion.setText(Integer.toString(getTestQuestions().size()));
            currentMaxPoints.setText(Integer.toString(getSelectedTest().gettMaxPoints()));
        });
    }

    // Loads all data from db on selected test
    public void loadData() {
        testBoxIndex = testBox.getSelectionModel().getSelectedIndex();
        // Chosen Test is saved as selectedTest
        setSelectedTest(tests.get(testBoxIndex));

        // Questions are saved to testQuestions List
        setTestQuestions(QuestionService.read(getSelectedTest().getTestId()));

        // testAnswers[0] will contain a List with all answers for 1st Question, testAnswers[1] all answers for 2nd Question etc.
        setTestAnswers(new ArrayList<>());
        for(Question q : getTestQuestions()) {
            getTestAnswers().add(AnswerService.read(q.getQuestionId()));
        }
    }


    static public Test getSelectedTest() {
        return selectedTest;
    }

    public void setSelectedTest(Test selectedTest) {
        this.selectedTest = selectedTest;
    }

    static public List<Question> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(List<Question> testQuestions) {
        this.testQuestions = testQuestions;
    }

    static public List<List> getTestAnswers() {
        return testAnswers;
    }

    public void setTestAnswers(List<List> testAnswers) {
        this.testAnswers = testAnswers;
    }
}
