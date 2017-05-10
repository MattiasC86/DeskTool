package view.createTest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.MenuBarAdmin;

public class FxView {



    Label numberOfQuestion;
    ListView<Pane> QuestionList;

    public FxView(Stage window) {

        ObservableList<Object> items = FXCollections.observableArrayList ();

        int counter = 1;

        Pane pane = new Pane();

        Label labeltitel = new Label("Titel:");
        labeltitel.setStyle("-fx-font-size: 20pt");
        labeltitel.relocate(600, 50);
        pane.getChildren().add(labeltitel);

        TextField titleTest = new TextField();
        titleTest.setPromptText("Titel");
        titleTest.setStyle("-fx-font-size: 14pt");
        titleTest.relocate(660, 50);
        pane.getChildren().add(titleTest);

        CheckBox box = new CheckBox("Självrättande");
        box.setScaleX(1.5);
        box.setScaleY(1.5);
        box.relocate(1000, 60);
        pane.getChildren().add(box);

        CheckBox box1 = new CheckBox("Tidsbegränsning");
        box1.setScaleX(1.5);
        box1.setScaleY(1.5);
        box1.relocate(650, 140);
        pane.getChildren().add(box1);

        TextField minutesField = new TextField();
        minutesField.setPromptText("Minuter");
        minutesField.setStyle("-fx-font-size: 14pt");
        minutesField.relocate(840, 130);
        minutesField.setVisible(false);
        pane.getChildren().add(minutesField);

        box1.setOnAction(e->{
            if(box1.isSelected()){
                minutesField.setVisible(true);
            }
            else{
                minutesField.setVisible(false);
            }
        });

        numberOfQuestion = new Label("Antal frågor: 0");
        numberOfQuestion.setStyle("-fx-font-size: 20pt");
        numberOfQuestion.relocate(1300, 50);
        pane.getChildren().add(numberOfQuestion);

        QuestionList = new ListView<Pane>();
        QuestionList.setPrefSize(1200, 600);
        QuestionList.relocate(300, 200);
        pane.getChildren().add(QuestionList);

        Button btn = new Button("Ett svar fråga");
        btn.relocate(300, 50);
        pane.getChildren().add(btn);

        btn.setOnAction(e->{
            OneQuestion OneQuestion = new OneQuestion(QuestionList);
            items.add(OneQuestion);
            setNumberOfQuestions();
            System.out.println(items);
        });

        Button btn1 = new Button("Flera Svar fråga");
        btn1.relocate(300, 100);
        pane.getChildren().add(btn1);

        btn1.setOnAction(e->{
            ManyQuestion ManyQuestion = new ManyQuestion(QuestionList);
            items.add(ManyQuestion);
            setNumberOfQuestions();
            System.out.println(items);
        });

        Button btn2 = new Button("Rangordnings fråga");
        btn2.relocate(300, 150);
        pane.getChildren().add(btn2);

        btn2.setOnAction(e->{
            RankQuestion RankQuestion = new RankQuestion(QuestionList);
            items.add(RankQuestion);
            setNumberOfQuestions();
        });

        Button btn3 = new Button("Ta bort markerad fråga");
        btn3.relocate(1300, 150);
        pane.getChildren().add(btn3);

        btn3.setOnAction(e->{
            items.remove(QuestionList.getSelectionModel().getSelectedIndex());
            QuestionList.getItems().remove(QuestionList.getSelectionModel().getSelectedItem());
            setNumberOfQuestions();
            System.out.println(items);
        });


        Button btnSaveTest = new Button("Spara Test");
        btnSaveTest.relocate(1390, 820);
        btnSaveTest.setStyle("-fx-font-size: 14pt");
        pane.getChildren().add(btnSaveTest);

        btnSaveTest.setOnAction(e->{

        });




        BorderPane bp = new BorderPane();
        Pane menubarpane = new Pane();
        MenuBarAdmin mba = new MenuBarAdmin(menubarpane, window);
        bp.setTop(menubarpane);
        bp.setCenter(pane);

        window.setTitle("Skapa test");
        window.setOnCloseRequest(e -> Platform.exit());
        Scene scene = new Scene(bp, 1600, 900);
        //PrimaryStage.setMaximized(true);
        window.setScene(scene);
        window.show();


    }



    private void setNumberOfQuestions(){
        numberOfQuestion.setText("Antal frågor: " + QuestionList.getItems().size());
    }

}

