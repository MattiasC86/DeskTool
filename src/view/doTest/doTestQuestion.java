package view.doTest;

import entity.Answer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Henrik on 2017-05-24.
 */
public class doTestQuestion {

    private int aNumber;
    private List <Answer> aList;
    private Pane pane;
    private ListView<Pane> testList;
    public ListView<String> rankQuestionList;

    //I klassen
    Label label;
    Label label1;

    public doTestQuestion(int answerNumber, String Question, List<Answer> answersList, ListView<Pane> testList){

        this.aNumber = answerNumber;
        this.aList = answersList;
        this.testList = testList;

        pane = new Pane();
        pane.getStyleClass().add("QuestionPane");
        pane.setPrefHeight(200);

        label = new Label();
        label.setStyle("-fx-font-size: 14pt; -fx-underline: true");
        label.relocate(20, 5);
        pane.getChildren().add(label);

        label1 = new Label(Question);
        label1.setStyle("-fx-font-size: 14pt");
        label1.relocate(20, 50);
        pane.getChildren().add(label1);
    }

    public CheckBox[] answerBox;

    public void singleQuestion(){

        label.setText("Envalsfråga (Ett rätt svar)");

        answerBox = new CheckBox[aNumber];
        for (int i = 0; i < aNumber; i++) {
            answerBox[i] = new CheckBox(aList.get(i).getaText());
            answerBox[i].relocate(20 + (i * 190), 100);
            pane.getChildren().add(answerBox[i]);

            final int x = i;

            answerBox[i].setOnAction(e -> {
                for (int d = 0; d < aNumber; d++) {
                    answerBox[d].setSelected(false);
                }
                answerBox[x].setSelected(true);
            });
        }
        testList.getItems().add(pane);
    }

    public void manyQuestion(){

        label.setText("Flervalsfråga (Ett eller flera rätta svar)");

        answerBox = new CheckBox[aNumber];

        for (int i = 0; i < aNumber; i++) {
            answerBox[i] = new CheckBox(aList.get(i).getaText());
            answerBox[i].relocate(20 + (i * 190), 100);
            pane.getChildren().add(answerBox[i]);
        }
        testList.getItems().add(pane);
    }

    public void rankedQuestion(){

        pane.setPrefHeight(230);
        label.setText("Rangordningsfråga (Rangordna svaren uppifrån och ner):");
        label1.relocate(510, 5);

        rankQuestionList = new ListView<String>();
        rankQuestionList.setPrefSize(1140, 150);
        rankQuestionList.relocate(20, 45);
        rankQuestionList.setStyle("-fx-font-size: 12pt");
        pane.getChildren().add(rankQuestionList);

        ObservableList<String> Strings = FXCollections.observableArrayList();

        rankQuestionList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>(){
                @Override
                public void updateItem(String item , boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };

            cell.setOnDragDetected(event -> {
                if (cell.getItem() == null) {
                    return;
                }
                ObservableList<String> items = cell.getListView().getItems();
                Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(cell.getItem());
                dragboard.setContent(content);
                event.consume();
            });

            cell.setOnDragOver(event -> {
                if (event.getGestureSource() != cell &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            cell.setOnDragEntered(event -> {
                if (event.getGestureSource() != cell &&
                        event.getDragboard().hasString()) {

                }
            });

            cell.setOnDragExited(event -> {
                if (event.getGestureSource() != cell &&
                        event.getDragboard().hasString()) {

                }
            });

            cell.setOnDragDropped(event -> {
                if (cell.getItem() == null) {
                    return;
                }

                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasString()) {
                    ObservableList<String> items = cell.getListView().getItems();
                    int draggedIdx = items.indexOf(db.getString());
                    int thisIdx = items.indexOf(cell.getItem());

                    items.set(draggedIdx, cell.getItem());
                    items.set(thisIdx, db.getString());

                    List<String> itemscopy = new ArrayList<>(cell.getListView().getItems());
                    cell.getListView().getItems().setAll(itemscopy);

                    success = true;
                }
                event.setDropCompleted(success);

                event.consume();
            });

            cell.setOnDragDone(DragEvent::consume);

            return cell ;
        });

        Collections.shuffle(aList);
        for(int i = 0; i < aList.size(); i++){
            rankQuestionList.getItems().add(aList.get(i).getaText());
        }
        testList.getItems().add(pane);
    }


}
