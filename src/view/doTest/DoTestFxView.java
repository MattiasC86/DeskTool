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

    //This class is for when you actually do the test
    public DoTestFxView(Stage window) {
        this.window = window;

        Pane pane = new Pane();
        pane.getStyleClass().add("BackGroundPane");

        Pane contentPane = new Pane();
        contentPane.setPrefSize(1300,850);
        contentPane.relocate(150,10);
        contentPane.getStyleClass().add("ContentPane");
        pane.getChildren().add(contentPane);

        labeltitel = new Label("Prov:");
        labeltitel.setStyle("-fx-font-size: 30  pt");
        labeltitel.relocate(50, 50);
        contentPane.getChildren().add(labeltitel);

        //Antal frågor och tid
        testInfo = new Label();
        testInfo.setStyle("-fx-font-size: 20pt");
        testInfo.relocate(350, 70);
        contentPane.getChildren().add(testInfo);

        timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 20pt");
        timeLabel.relocate(950, 70);
        contentPane.getChildren().add(timeLabel);

        testList = new ListView<Pane>();
        testList.setPrefSize(1200, 600);
        testList.relocate(50, 150);
        testList.setFocusTraversable( false );
        contentPane.getChildren().add(testList);

        Button testDone = new Button("Lämna in");
        testDone.relocate(1100, 770);
        testDone.setPrefSize(150,50);
        testDone.setStyle("-fx-font-size: 16pt");
        contentPane.getChildren().add(testDone);

        // Turning in the test
        testDone.setOnAction(e->{
            messageBox("Test inlämnat! Bra jobbat!");
            handInTest();
        });

        window.setTitle("Göra test");
        window.setOnCloseRequest(e -> Platform.exit());
        Scene scene = new Scene(pane, 1600, 900);
        scene.getStylesheets().add("css/TestViewCSS.css");
        window.setScene(scene);
        window.show();
    }

    int handInMinutes;
    int handInSeconds;

    //Get the test info and set it
    public void setTestInfo(String titel, int number, int time){

        labeltitel.setText(titel);
        testInfo.setText("Antal frågor: " + number + "          Tidsgräns(minuter): " + time);

        //Countdown
        int minutes = time;
        if(minutes == 0){
            //DO nothing no timer
            timeLabel.setText("Ingen tidsgräns");
        }
        else{

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int calcMinutes = minutes - 1;
                int secondsLeft = 60;

                @Override
                public void run() {
                    secondsLeft--;


                    Platform.runLater(() -> timeLabel.setText("Minuter: " + calcMinutes + " Sekunder: " + secondsLeft));
                    if(secondsLeft == 0){
                        if(calcMinutes == 0 && secondsLeft == 0){
                            handInMinutes = calcMinutes;
                            handInSeconds = secondsLeft;
                            timer.cancel();
                            Platform.runLater(() -> messageBox("Tiden är nu ute! Ditt test har lämnats in!"));
                            Platform.runLater(() -> handInTest());
                        }
                        calcMinutes--;
                        secondsLeft = 60;
                    }
                    handInMinutes = SelectTestView.getSelectedTest().gettTimeMin() - calcMinutes - 1;
                    handInSeconds = 60 - secondsLeft;
                }


            }, 0, 1000);



        }
    }

    //Sends the the to the database
    public void handInTest(){

        int numberOfQuestion = testList.getItems().size();

        // List with Stuff in
        Test selectedTest = SelectTestView.getSelectedTest();
        List<Question> qList = SelectTestView.testQuestions;
        List<List> aListList = SelectTestView.testAnswers;
        List<doTestQuestion> qListGraphicObject = SelectTestView.doTestQuestionsList;

        int finalHandInSeonds = (handInMinutes * 60) +handInSeconds;




        // BEHÖVER LÄGGAS IN TIMESEC
        AnsweredTest answeredTest = new AnsweredTest(false, selectedTest.gettDisplayResult(), 0, finalHandInSeonds, "", UserService.read(LoginLogic.getCurrId()), selectedTest);

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
        else if(user.getRole().equalsIgnoreCase("Teacher")){
            TeacherFirstpage tfp = new TeacherFirstpage(window);
        }
        else if(user.getRole().equalsIgnoreCase("Student")){
            StudentFirstpage sfp = new StudentFirstpage(window);
        }
    }

    //Popup messagebox
    private void messageBox(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information saknas");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}

