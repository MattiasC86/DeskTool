package view.doTest;


        import java.util.*;

        import entity.*;
        import javafx.application.Platform;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;
        import logic.LoginLogic;
        import service.*;
        import view.homepage.AdminFirstpage;
        import view.homepage.StudentFirstpage;
        import view.homepage.TeacherFirstpage;

public class DoTestFxView {


    ListView<Pane> testList;
    Label labeltitel;
    Label testInfo;
    Label timeLabel;
    Stage window;

    public DoTestFxView(Stage window) {
        this.window = window;

        Pane pane = new Pane();

        labeltitel = new Label("Prov:");
        labeltitel.setStyle("-fx-font-size: 40pt");
        labeltitel.relocate(300, 50);
        pane.getChildren().add(labeltitel);

        //Antal frågor och tid
        testInfo = new Label();
        testInfo.setStyle("-fx-font-size: 20pt");
        testInfo.relocate(600, 70);
        pane.getChildren().add(testInfo);

        timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 20pt");
        timeLabel.relocate(1200, 70);
        pane.getChildren().add(timeLabel);

        testList = new ListView<Pane>();
        testList.setPrefSize(1200, 600);
        testList.relocate(300, 200);
        testList.setFocusTraversable( false );
        pane.getChildren().add(testList);

        Button testDone = new Button("Lämna in");
        testDone.relocate(1350, 820);
        testDone.setPrefSize(150,50);
        testDone.setStyle("-fx-font-size: 16pt");
        pane.getChildren().add(testDone);

        // Turning in the test
        testDone.setOnAction(e->{
            messageBox("Test inlämnat! Bra jobbat!");
            handInTest();
        });

        window.setTitle("Göra test");
        window.setOnCloseRequest(e -> Platform.exit());
        Scene scene = new Scene(pane, 1600, 900);
        scene.getStylesheets().add("css/testCSS.css");
        window.setScene(scene);
        window.show();
    }

    public void setTestInfo(String titel, int number, int time){

        labeltitel.setText(titel);
        testInfo.setText("Antal frågor: " + number + "          Tidsgräns(minuter): " + time);

        //Countdown
        int minutes = time;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int calcMinutes = minutes - 1;
            int seconds = 60;
            @Override
            public void run() {
                seconds--;
                Platform.runLater(() -> timeLabel.setText("Minuter: " + calcMinutes + " Sekunder: " + seconds));
                if(seconds == 0){
                    if(calcMinutes == 0 && seconds == 0){
                        timer.cancel();
                        Platform.runLater(() -> messageBox("Tiden är nu ute! Ditt test har lämnats in!"));
                        Platform.runLater(() -> handInTest());
                    }
                    calcMinutes--;
                    seconds = 60;
                }
            }
        }, 0, 1000);
    }

    public void handInTest(){

        int numberOfQuestion = testList.getItems().size();

        // List with Stuff in
        Test selectedTest = SelectTestView.getSelectedTest();
        List<Question> qList = SelectTestView.testQuestions;
        List<List> aListList = SelectTestView.testAnswers;
        List<doTestQuestion> qListGraphicObject = SelectTestView.doTestQuestionsList;

        // BEHÖVER LÄGGAS IN TIMESEC
        AnsweredTest answeredTest = new AnsweredTest(false, selectedTest.gettDisplayResult(), 0, 10, "", UserService.read(LoginLogic.getCurrId()), selectedTest);

        // Will contain all useranswers
        List<UserAnswer> userAnsweredList = new ArrayList<>();

        // Loops through questions one by one
        for(int i = 0; i < numberOfQuestion; i++){
            // Single or multiple question
            if(qList.get(i).getqType().equalsIgnoreCase("Single") || qList.get(i).getqType().equalsIgnoreCase("Multiple")){
                List<Answer> currentAnswerList = aListList.get(i);
                for(int d = 0; d < currentAnswerList.size(); d++){
                    int checked = 0;
                    if(qListGraphicObject.get(i).answerBox[d].isSelected()){
                        checked = 1;
                    }
                    UserAnswer currentUserAnswer = new UserAnswer(checked, d, qListGraphicObject.get(i).answerBox[d].getText(), qList.get(i), currentAnswerList.get(d), answeredTest);
                    userAnsweredList.add(currentUserAnswer);
                }
            }
            // Ranked question
            else if(qList.get(i).getqType().equalsIgnoreCase("Ranked")){
                List<Answer> currentAnswerList = aListList.get(i);
                for(int d = 0; d < currentAnswerList.size(); d++){
                    UserAnswer currentUserAnswer = new UserAnswer(0, d, qListGraphicObject.get(i).rankQuestionList.getItems().get(d), qList.get(i), currentAnswerList.get(d), answeredTest);
                    userAnsweredList.add(currentUserAnswer);
                }
            }
        }
        AnsweredTestService.create(answeredTest, userAnsweredList);

        User user = UserService.read(LoginLogic.getCurrId());
        if(user.getRole().equalsIgnoreCase("Admin")){
            AdminFirstpage afp = new AdminFirstpage(window);
        }
        else if(user.getRole().equalsIgnoreCase("Lärare")){
            TeacherFirstpage tfp = new TeacherFirstpage(window);
        }
        else if(user.getRole().equalsIgnoreCase("Student")){
            StudentFirstpage sfp = new StudentFirstpage(window);
        }
    }

    private void messageBox(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information saknas");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}

