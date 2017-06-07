package view.createTest;

import entity.Question;
import entity.User;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import logic.TestLogic;
import service.UserService;
import view.homepage.AdminFirstpage;
import view.homepage.TeacherFirstpage;
import view.menuBars.MenuBarAdmin;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class FxView {

    Label numberOfQuestion;
    ListView<Pane> QuestionList;
    TextField titleTest;
    CheckBox box1;
    TextField minutesField;

    ArrayList<PreQuestion> questions = new ArrayList<PreQuestion>();

    Date startDate;
    Date endDate;

    public FxView(Stage window) {

        Pane pane = new Pane();
        pane.getStyleClass().add("BackGroundPane");

        Pane contentPane = new Pane();
        contentPane.setPrefSize(1300,850);
        contentPane.relocate(150,10);
        contentPane.getStyleClass().add("ContentPane");
        pane.getChildren().add(contentPane);

        Label labeltitel = new Label("Titel:");
        labeltitel.setStyle("-fx-font-size: 20pt");
        labeltitel.relocate(300, 15);
        contentPane.getChildren().add(labeltitel);

        titleTest = new TextField();
        titleTest.setPromptText("Titel");
        titleTest.setStyle("-fx-font-size: 14pt");
        titleTest.relocate(360, 15);
        contentPane.getChildren().add(titleTest);

        CheckBox box = new CheckBox("Låt elever se resultat");
        box.setScaleX(1.5);
        box.setScaleY(1.5);
        box.relocate(800, 35);
        pane.getChildren().add(box);

        box1 = new CheckBox("Tidsbegränsning");
        box1.setScaleX(1.5);
        box1.setScaleY(1.5);
        box1.relocate(340, 75);
        contentPane.getChildren().add(box1);

        final DatePicker datePickerStartDate = new DatePicker();
        datePickerStartDate.setOnAction(t -> {
            LocalDate locStartDate = datePickerStartDate.getValue();
            startDate = Date.valueOf(locStartDate);
            System.err.println("Selected date: " + startDate);
        });
        datePickerStartDate.setPromptText("Startdatum");
        datePickerStartDate.relocate(305,115);
        contentPane.getChildren().add(datePickerStartDate);

        final DatePicker datePickerEndDate = new DatePicker();
        datePickerEndDate.setOnAction(t -> {
            LocalDate locEndDate = datePickerEndDate.getValue();
            endDate = Date.valueOf(locEndDate);
            System.err.println("Selected date: " + endDate);
        });
        datePickerEndDate.setPromptText("Slutdatum");
        datePickerEndDate.relocate(540,115);
        contentPane.getChildren().add(datePickerEndDate);



        minutesField = new TextField();
        minutesField.setPromptText("Minuter");
        minutesField.setStyle("-fx-font-size: 14pt");
        minutesField.relocate(530, 65);
        minutesField.setVisible(false);
        contentPane.getChildren().add(minutesField);

        box1.setOnAction(e->{
            if(box1.isSelected()){
                minutesField.setVisible(true);
            }
            else{
                minutesField.setVisible(false);
            }
        });

        numberOfQuestion = new Label("Antal frågor: 0");
        numberOfQuestion.setStyle("-fx-font-size: 20pt; -fx-border-width: 2pt");
        numberOfQuestion.relocate(1050, 20);
        contentPane.getChildren().add(numberOfQuestion);

        QuestionList = new ListView<Pane>();
        QuestionList.setStyle("-fx-border-color: black");
        QuestionList.setPrefSize(1200, 600);
        QuestionList.relocate(50, 160);
        contentPane.getChildren().add(QuestionList);

        Button btn = new Button("Envalsfråga");
        btn.setStyle("-fx-font-size: 14pt");
        btn.setPrefWidth(200);
        btn.relocate(50, 10);
        contentPane.getChildren().add(btn);

        btn.setOnAction(e->{
            PreQuestion Question1 = new PreQuestion(QuestionList);
            Question1.oneAnswerQuestion();
            questions.add(Question1);
            setNumberOfQuestions();
        });

        Button btn1 = new Button("Flervalsfråga");
        btn1.setStyle("-fx-font-size: 14pt");
        btn1.setPrefWidth(200);
        btn1.relocate(50, 60);
        contentPane.getChildren().add(btn1);

        btn1.setOnAction(e->{
            PreQuestion Question2 = new PreQuestion(QuestionList);
            Question2.manyAnswerQuestion();
            questions.add(Question2);
            setNumberOfQuestions();
        });

        Button btn2 = new Button("Rangordningsfråga");
        btn2.setStyle("-fx-font-size: 14pt");
        btn2.setPrefWidth(200);
        btn2.relocate(50, 110);
        contentPane.getChildren().add(btn2);

        btn2.setOnAction(e->{
            PreQuestion Question3 = new PreQuestion(QuestionList);
            Question3.rankedQuestion();
            questions.add(Question3);
            setNumberOfQuestions();
        });

        Button btn3 = new Button("Ta bort markerad fråga");
        btn3.setStyle("-fx-font-size: 12pt");
        btn3.setPrefWidth(200);
        btn3.relocate(1050, 110);
        contentPane.getChildren().add(btn3);

        btn3.setOnAction(e->{
            questions.remove(QuestionList.getSelectionModel().getSelectedIndex());
            QuestionList.getItems().remove(QuestionList.getSelectionModel().getSelectedItem());
            setNumberOfQuestions();
        });

        Button btnSaveTest = new Button("Spara Test");
        btnSaveTest.relocate(1100, 770);
        btnSaveTest.setPrefWidth(150);
        btnSaveTest.setStyle("-fx-font-size: 14pt");
        contentPane.getChildren().add(btnSaveTest);

        btnSaveTest.setOnAction(e->{

            if(validateInput() == false){
                return;
            }

            int showResult;
            if(box.isSelected()){
                showResult = 1;
            }
            else{
                showResult = 0;
            }

            int timeLimit = 0;
            if(box1.isSelected()){

                String text = minutesField.getText();
                timeLimit = Integer.parseInt(text);
            }
            else{
                timeLimit = 0;
            }


            TestLogic.saveTest(questions, titleTest.getText(), 1, timeLimit, showResult, startDate, endDate, UserService.read(LoginLogic.getCurrId()));

            messageBox("Test skapat!");

            User user = UserService.read(LoginLogic.getCurrId());
            if(user.getRole().equalsIgnoreCase("Admin")){
                AdminFirstpage afp = new AdminFirstpage(window);
            }
            else if(user.getRole().equalsIgnoreCase("Teacher")){
                TeacherFirstpage tfp = new TeacherFirstpage(window);
            }
        });

        BorderPane bp = new BorderPane();
        Pane menubarpane = new Pane();
        MenuBarAdmin mba = new MenuBarAdmin(menubarpane, window);
        bp.setTop(menubarpane);
        bp.setCenter(pane);

        bp.setId("createTestPane");

        window.setTitle("Skapa test");
        window.setOnCloseRequest(e -> Platform.exit());
        Scene scene = new Scene(bp, 1600, 900);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/TestViewCSS.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }

    private void setNumberOfQuestions(){
        numberOfQuestion.setText("Antal frågor: " + QuestionList.getItems().size());
    }

    private boolean validateInput(){
        //Kolla titel är anvigen
        if(titleTest.getText().isEmpty()){
            messageBox("Ange titel!");
            return false;
        }
        //Kolla om tid är angiver
        if(box1.isSelected()){
            if(minutesField.getText().isEmpty()){
                messageBox("Ange antal minuter!");
                return false;
            }

            if (minutesField.getText().matches("[0-9]+")) {
                String text = minutesField.getText();
                if(Integer.parseInt(text) == 0){
                    messageBox("Tidsgränsen kan inte vara noll ");
                    return false;
                }
                //ok
            }
            else{
                messageBox("Antal minuter får bara bestå av siffror!");
                return false;
            }
        }
        //Kolla så provet inte är tomt
        if(QuestionList.getItems().size() == 0){
            messageBox("Provet måste minst ha en fråga!");
            return false;
        }

        //Kolla frågor
        for(int i = 0; i < questions.size(); i++){

            if(questions.get(i).QuestionField.getText().isEmpty()){
                messageBox("Ange fråga nr: " + (i + 1));
                return false;
            }


            //Envalsfråga bara 1 alternativ får vara rätt
            if(questions.get(i).getType() == 0){
                int answers = questions.get(i).CBox.getValue();
                int correctAnswerCount = 0;

                for(int d = 0; d < answers; d++){
                    if(questions.get(i).answerField[d].getText().isEmpty()){
                        messageBox("Ange svar fråga: " + (i + 1) + " Rad: " + (d + 1));
                        return false;
                    }
                    if(questions.get(i).answerBox[d].isSelected()) {
                        correctAnswerCount++;
                    }
                }
                if (correctAnswerCount == 1) {
                    //Ok
                }
                else{
                    messageBox("Markera rätt svar fråga: " + (i + 1));
                    return false;
                }
            }
            //Flervalsfråga
            else if (questions.get(i).getType() == 1){

                int answers = questions.get(i).CBox.getValue();
                int correctAnswerCount = 0;

                for(int d = 0; d < answers; d++){
                    if(questions.get(i).answerField[d].getText().isEmpty()){
                        messageBox("Ange svar fråga: " + (i + 1) + " Rad: " + (d + 1));
                        return false;
                    }
                    if(questions.get(i).answerBox[d].isSelected()) {
                        correctAnswerCount++;
                    }
                }

                if (correctAnswerCount >= 1) {
                    //Ok
                }
                else{
                    messageBox("Markera minst ett rätt svar fråga: " + (i + 1));
                    return false;
                }
            }
            //Rangordningsfråga
            else if (questions.get(i).getType() == 2) {
                int answers = questions.get(i).CBox.getValue();

                for (int d = 0; d < answers; d++) {
                    if (questions.get(i).answerField[d].getText().isEmpty()) {
                        messageBox("Ange svar fråga: " + (i + 1) + " Rad: " + (d + 1));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void messageBox(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information saknas");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}

