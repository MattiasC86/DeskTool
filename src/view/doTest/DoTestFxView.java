package view.doTest;


        import java.util.*;

        import entity.*;
        import javafx.application.Application;
        import javafx.application.Platform;
        import javafx.beans.property.ObjectProperty;
        import javafx.beans.property.SimpleObjectProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.input.*;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;
        import logic.LoginLogic;
        import logic.TestLogic;
        import logic.UserLogic;
        import service.*;

public class DoTestFxView {


    ListView<Pane> TestList;
    Label labeltitel;
    Label testInfo;
    Label timeLabel;

    public DoTestFxView(Stage window) {

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

        TestList = new ListView<Pane>();
        TestList.setPrefSize(1200, 600);
        TestList.relocate(300, 200);
        TestList.setFocusTraversable( false );
        pane.getChildren().add(TestList);

        Button testDone = new Button("Lämna in");
        testDone.relocate(1350, 820);
        testDone.setPrefSize(150,50);
        testDone.setStyle("-fx-font-size: 16pt");
        pane.getChildren().add(testDone);




        testDone.setOnAction(e->{
            int numberOfQuestion = TestList.getItems().size();

            //List with Stuff in
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
                    calcMinutes--;
                    seconds = 60;
                    if(calcMinutes <= 0){
                        System.out.println("Tiden ute");
                        timer.cancel();
                    }
                }
            }
        }, 0, 1000);
    }

}

