package view.doTest;


        import java.util.ArrayList;
        import java.util.List;

        import entity.Question;
        import entity.Test;
        import entity.User;
        import javafx.application.Application;
        import javafx.application.Platform;
        import javafx.beans.property.ObjectProperty;
        import javafx.beans.property.SimpleObjectProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.input.ClipboardContent;
        import javafx.scene.input.DragEvent;
        import javafx.scene.input.Dragboard;
        import javafx.scene.input.TransferMode;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;
        import logic.LoginLogic;
        import logic.UserLogic;
        import service.AnswerService;
        import service.QuestionService;
        import service.UserService;

public class DoTestFxView extends Application {

    public static void main(String[] args) {
        launch();
    }

    ListView<Pane> TestList;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Pane pane = new Pane();

        //------GETTING TEST, QUESTIONS AND ANSWERS FROM DB---------
        User user = UserService.read(1);
        List<Test> tests = UserLogic.getAvailableTests(user);
        List<String> testTitles = new ArrayList<>();
        for(Test element : tests) {
            testTitles.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testTitles
                );
        final ComboBox testBox = new ComboBox(availableTests);
        pane.getChildren().add(testBox);
        int testBoxIndex = testBox.getSelectionModel().getSelectedIndex();

        // Chosen Test is saved as selectedTest
        //Test selectedTest = tests.get(testBoxIndex);

        // Questions are saved to testQuestions List
        //List<Question> testQuestions = QuestionService.read(selectedTest.getTestId());

        // testAnswers[0] will contain a List with all answers for 1st Question, testAnswers[1] all answers for 2nd Question etc.
       /* List<List> testAnswers = new ArrayList<>();
        for(Question q : testQuestions) {
            testAnswers.add(AnswerService.read(q.getQuestionId()));
        }*/

        Label labeltitel = new Label("Prov:");
        labeltitel.setStyle("-fx-font-size: 40pt");
        labeltitel.relocate(300, 50);
        pane.getChildren().add(labeltitel);

        TestList = new ListView<Pane>();
        TestList.setPrefSize(1200, 600);
        TestList.relocate(300, 200);
        pane.getChildren().add(TestList);

        PrimaryStage.setTitle("Göra test");
        PrimaryStage.setOnCloseRequest(e -> Platform.exit());
        Scene scene = new Scene(pane, 800, 600);
        PrimaryStage.setMaximized(true);
        PrimaryStage.setScene(scene);
        PrimaryStage.show();

        addOneQuestion(6);
        addManyQuestion(4);
        addRankQuestion(3);
    }

    private void addOneQuestion(int answerNumber) {

        Pane pane = new Pane();
        pane.setStyle("-fx-border-color: black");
        pane.setPrefHeight(200);

        Label label = new Label("Envalsfråga");
        label.setStyle("-fx-font-size: 18pt; -fx-underline: true");
        label.relocate(20, 5);
        pane.getChildren().add(label);

        Label label1 = new Label("Frågan....");
        label1.setStyle("-fx-font-size: 14pt");
        label1.relocate(20, 50);
        pane.getChildren().add(label1);

        CheckBox[] answerBox = new CheckBox[answerNumber];

        for (int i = 0; i < answerNumber; i++) {
            answerBox[i] = new CheckBox("Svar: " + (i + 1));
            answerBox[i].relocate(20 + (i * 190), 100);
            pane.getChildren().add(answerBox[i]);

            final int x = i;

            answerBox[i].setOnAction(e -> {
                for (int d = 0; d < answerNumber; d++) {
                    answerBox[d].setSelected(false);
                }
                answerBox[x].setSelected(true);
            });
        }

        TestList.getItems().add(pane);
    }

    private void addManyQuestion(int answerNumber) {

        Pane pane = new Pane();
        pane.setStyle("-fx-border-color: black");
        pane.setPrefHeight(200);

        Label label = new Label("Flervalsfråga");
        label.setStyle("-fx-font-size: 18pt; -fx-underline: true");
        label.relocate(20, 5);
        pane.getChildren().add(label);

        Label label1 = new Label("Frågan....");
        label1.setStyle("-fx-font-size: 14pt");
        label1.relocate(20, 50);
        pane.getChildren().add(label1);

        CheckBox[] answerBox = new CheckBox[answerNumber];

        for (int i = 0; i < answerNumber; i++) {
            answerBox[i] = new CheckBox("Svar: " + (i + 1));
            answerBox[i].relocate(20 + (i * 190), 100);
            pane.getChildren().add(answerBox[i]);
        }

        TestList.getItems().add(pane);

    }


    private final ObjectProperty<ListCell<String>> dragSource = new SimpleObjectProperty<>();

    private void addRankQuestion(int answerNumber) {

        Pane pane = new Pane();
        pane.setStyle("-fx-border-color: black");
        pane.setPrefHeight(200);

        Label label = new Label("Rangordningsfråga");
        label.setStyle("-fx-font-size: 18pt; -fx-underline: true");
        label.relocate(20, 5);
        pane.getChildren().add(label);

        Label label1 = new Label("Frågan....");
        label1.setStyle("-fx-font-size: 14pt");
        label1.relocate(250, 10);
        pane.getChildren().add(label1);

        ListView<String> rankQuestionList = new ListView<String>();
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






        rankQuestionList.getItems().add("Andra");
        rankQuestionList.getItems().add("Första");
        rankQuestionList.getItems().add("Fjärde");
        rankQuestionList.getItems().add("Tredje");


        TestList.getItems().add(pane);


    }

}

