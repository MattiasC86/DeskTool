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
import logic.LoginLogic;
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
    User user;

    private ComboBox testBox;
    private List<Test> tests;
    private int testBoxIndex;

    //These variables have getters and will hold the selected test, questions and answers
    static private Test selectedTest;
    static private List<Question> testQuestions;
    static private List<List> testAnswers;

    public SelectTestView(Stage window) {

        window.setTitle("Välj test");

        Pane pane = new Pane();

        user = UserService.read(LoginLogic.getCurrId());
        tests = UserLogic.getAvailableTests(user);
        
        MenuBarAdmin x = new MenuBarAdmin(pane, window);

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

        // Redirects user to DoTestFXView
        startTest.setOnAction(e->{
            DoTestFxView dtfv = new DoTestFxView(window);

            /*
            static private Test selectedTest;
            static private List<Question> testQuestions;
            static private List<List> testAnswers;
           */



            for(int i = 0; i < testAnswers.size(); i++) {

                List dd = testAnswers.get(i);

                for(int d = 0; d < testAnswers.get(i).size(); d++) {


                    //System.out.println(dd.get(d).);

                }


            }


            dtfv.setTestInfo(selectedTest.gettTitle(), testQuestions.size(), selectedTest.gettTimeMin());

            for(int i = 0; i < testQuestions.size(); i++) {

                String qType = testQuestions.get(0).getqType();

                if (qType.equalsIgnoreCase("Single")) {
                    dtfv.addOneQuestion(2);



                }


                else if (qType.equalsIgnoreCase("Many")) {
                    dtfv.addManyQuestion(2);




                }


                else if (qType.equalsIgnoreCase("Ranked")) {
                    dtfv.addRankQuestion(2);



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
