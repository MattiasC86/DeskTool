package view.createTest;


import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;



public class PreQuestion {

    Pane QuestionPane;
    public CheckBox[] answerBox;
    public TextField[] answerField;
    Label[] answerLabel;
    public ComboBox<Integer> CBox;
    public TextField QuestionField;
    public Label label;

    int questionType;

    public PreQuestion(ListView<Pane> List){

            QuestionPane = new Pane();
            QuestionPane.setPrefHeight(200);
            QuestionPane.setStyle("-fx-border-color: black");
            QuestionPane.getStyleClass().add("QuestionPane");

            label = new Label();
            label.setStyle("-fx-font-size: 14pt; -fx-underline: true");
            label.relocate(20, 5);
            QuestionPane.getChildren().add(label);

            QuestionField = new TextField();
            QuestionField.setStyle("-fx-font-size: 14pt");
            QuestionField.setPromptText("Fråga");
            QuestionField.setMinWidth(400);
            QuestionField.relocate(20, 40);
            QuestionPane.getChildren().add(QuestionField);

            Label label1 = new Label("Antal svar:");
            label1.setStyle("-fx-font-size: 14pt; -fx-underline: true");
            label1.relocate(200, 5);
            QuestionPane.getChildren().add(label1);

            CBox = new ComboBox<Integer>();
            CBox.relocate(300, 5);
            CBox.getItems().addAll(2, 3, 4, 5, 6);
            QuestionPane.getChildren().add(CBox);
            CBox.setValue(2);
            CBox.setStyle("-fx-font-size: 10pt");

            CBox.setOnAction(e->{
                numberOfAnswers(CBox.getValue());
            });

            List.getItems().add(QuestionPane);
            List.scrollTo(List.getItems().size() - 1);
        }

    public void oneAnswerQuestion(){

        label.setText("Envalsfråga");

        answerBox = new CheckBox[6];
        answerField = new TextField[6];

        for(int i = 0; i < 6; i++){

            answerField[i] = new TextField();
            answerField[i].setPromptText("Svar");
            answerField[i].relocate(40, 110 + (i * 40));
            answerField[i].setPrefWidth(200);
            QuestionPane.getChildren().add(answerField[i]);

            answerBox[i] = new CheckBox("Rätt svar");
            answerBox[i].relocate(265, 115 + (i * 40));
            answerBox[i].setScaleX(1.3);
            answerBox[i].setScaleY(1.3);
            QuestionPane.getChildren().add(answerBox[i]);

            final int d = i;
            answerBox[i].setOnAction(e->{
                oneAnswerOnly();
                answerBox[d].setSelected(true);
            });
        }

        numberOfAnswers(2);

        questionType = 0;
    }

    public void manyAnswerQuestion(){

        label.setText("Flervalsfråga");

        answerBox = new CheckBox[6];
        answerField = new TextField[6];

        for(int i = 0; i < 6; i++){
            answerField[i] = new TextField();
            answerField[i].setPromptText("Svar");
            answerField[i].relocate(40, 110 + (i * 40));
            answerField[i].setPrefWidth(200);
            QuestionPane.getChildren().add(answerField[i]);

            answerBox[i] = new CheckBox("Rätt svar");
            answerBox[i].relocate(265, 115 + (i * 40));
            answerBox[i].setScaleX(1.3);
            answerBox[i].setScaleY(1.3);
            QuestionPane.getChildren().add(answerBox[i]);
        }
        numberOfAnswers(2);
        questionType = 1;
    }

    public void rankedQuestion(){

        label.setText("Rangordningsfråga");

        CBox.setOnAction(e->{
            numberOfAnswersRanked(CBox.getValue());
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
        numberOfAnswersRanked(2);
        questionType = 2;
    }

    private void numberOfAnswers(int number){

        for(int i = 0; i < 6; i++){
            answerBox[i].setVisible(false);
            answerField[i].setVisible(false);
        }

        for(int i = 0; i < number; i++){
            answerBox[i].setVisible(true);
            answerField[i].setVisible(true);
        }

        QuestionPane.setPrefHeight(200 + (number * 30));
    }

    private void numberOfAnswersRanked(int number){

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

    private void oneAnswerOnly(){

        for(int i = 0; i < 6; i++){
            answerBox[i].setSelected(false);
        }
    }

    public int getType(){
        return questionType;
    }

}
