package view.createTest;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class RankQuestion {

    Pane QuestionPane;
    Label[] answerLabel;
    TextField[] answerField;

    RankQuestion(ListView<Pane> List){

        QuestionPane = new Pane();
        QuestionPane.setPrefHeight(200);
        QuestionPane.setStyle("-fx-border-color: black");

        Label label = new Label("Rangordnings fråga");
        label.setStyle("-fx-font-size: 14pt; -fx-underline: true");
        label.relocate(20, 5);
        QuestionPane.getChildren().add(label);

        TextField QuestionField = new TextField();
        QuestionField.setStyle("-fx-font-size: 14pt");
        QuestionField.setPromptText("Fråga");
        QuestionField.setMinWidth(400);
        QuestionField.relocate(20, 40);
        QuestionPane.getChildren().add(QuestionField);

        Label label1 = new Label("Antal svar:");
        label1.setStyle("-fx-font-size: 14pt; -fx-underline: true");
        label1.relocate(200, 5);
        QuestionPane.getChildren().add(label1);

        ComboBox<Integer> CBox = new ComboBox<Integer>();
        CBox.relocate(300, 5);
        CBox.getItems().addAll(2, 3, 4, 5, 6);
        QuestionPane.getChildren().add(CBox);
        CBox.setValue(2);

        CBox.setOnAction(e->{
            numberOfAnswers(CBox.getValue());
        });

        answerLabel = new Label[6];
        answerField = new TextField[6];

        for(int i = 0; i < 6; i++){

            answerLabel[i] = new Label("" + (i + 1));
            answerLabel[i].setStyle("-fx-font-size: 12pt");
            answerLabel[i].relocate(40, 110 + (i * 43));
            QuestionPane.getChildren().add(answerLabel[i]);

            answerField[i] = new TextField();
            answerField[i].setPromptText("Svar");
            answerField[i].setStyle("-fx-font-size: 12pt");
            answerField[i].relocate(60, 110 + (i * 43));
            answerField[i].setPrefWidth(200);
            QuestionPane.getChildren().add(answerField[i]);

        }

        numberOfAnswers(2);

        List.getItems().add(QuestionPane);
        List.scrollTo(List.getItems().size() - 1);
    }

    private void numberOfAnswers(int number){

        for(int i = 0; i < 6; i++){
            answerLabel[i].setVisible(false);
            answerField[i].setVisible(false);
        }


        for(int i = 0; i < number; i++){
            answerLabel[i].setVisible(true);
            answerField[i].setVisible(true);
        }

        QuestionPane.setPrefHeight(200 + (number * 30));
    }

    public int getType(){
        return 2;
    }

}

