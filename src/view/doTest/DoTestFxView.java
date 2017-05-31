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

            //List to be inserted!
            List<AnsweredQuestion> answeredQList = new ArrayList<AnsweredQuestion>();
            List<UserAnswer> userAnsweredList = new ArrayList<>();

            int points = 0;

            for(int i = 0; i < numberOfQuestion; i++){

                AnsweredQuestion answeredQ = new AnsweredQuestion(0, qList.get(i), answeredTest);
                answeredQList.add(answeredQ);

                // Single or multiple question
                if(qList.get(i).getqType().equalsIgnoreCase("Single") || qList.get(i).getqType().equalsIgnoreCase("Multiple")){
                    List<Answer> currentAnswerList = aListList.get(i);
                    for(int d = 0; d < currentAnswerList.size(); d++){
                        int checked = 0;
                        if(qListGraphicObject.get(i).answerBox[d].isSelected()){
                            checked = 1;
                        }
                        UserAnswer currentUserAnswer = new UserAnswer(checked, d, qList.get(i), currentAnswerList.get(d), answeredTest);
                        userAnsweredList.add(currentUserAnswer);
                    }
                }
                // Ranked question  (Kommer funka men rankade frågor får ingen answersBox i doTestQuestio rankedQuestion() metoden
                else if(qList.get(i).getqType().equalsIgnoreCase("Ranked")){
                    List<Answer> currentAnswerList = aListList.get(i);
                    System.out.println("RANKED CURRLIST: " + currentAnswerList.get(i).getaText());
                    System.out.println("RANKED FRÅGA: " + qListGraphicObject.get(i).answerBox[0]);


                    int order = 0;
                    for(int d = 0; d < currentAnswerList.size(); d++){
                        for(int y = 0; y < qListGraphicObject.size(); y++) {
                            System.out.println("qList answerbox y: " + y + qListGraphicObject.get(i).answerBox[y].getText());
                            System.out.println("currentAnswer d: " + d + currentAnswerList.get(d).getaText());
                            if(qListGraphicObject.get(i).answerBox[y].getText().equals(currentAnswerList.get(d).getaText())){
                                order = y;
                            }
                            UserAnswer currentUserAnswer = new UserAnswer(0, order, qList.get(i), currentAnswerList.get(d), answeredTest);
                            userAnsweredList.add(currentUserAnswer);
                        }
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

