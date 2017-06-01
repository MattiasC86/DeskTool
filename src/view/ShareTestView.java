package view;

import entity.StudentGroup;
import entity.Test;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.LoginLogic;
import logic.SendMailLogic;
import service.*;
import view.homepage.Table;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-24.
 */
public class ShareTestView {
    User currUser;
    User selectedUser;

    StudentGroup selectedGrupp;

    SendMailLogic sm = new SendMailLogic();
    GridPane gp = new GridPane();

    TableView testTable;
    TableView<User> personTable;
    TableView<StudentGroup> gruppTable;

    Button shareBtn;
    Button mailBtn;

    List<Test> testList;
    //List<User> userList;
    //List<StudentGroup> groupList;

    /*

    -Välj test
        -Dela till elev/grupp
            -Välj specifik elev/grupp
            -Dela

    */

    public ShareTestView(Stage window) {
        currUser = UserService.read(LoginLogic.getCurrId());

        Pane menuPane = new Pane();
        MenuBarHelper.getMenuBar(window, menuPane);
        Pane mainPane = new Pane();

        ObservableList<Test> tsList = FXCollections.observableArrayList(TestService.readAll());
        ObservableList<User> userList = FXCollections.observableArrayList(UserService.readAll());
        ObservableList<StudentGroup> groupList = FXCollections.observableArrayList(StudentGroupService.readAll());


        // If logged in user is Admin, all tests are shown
        // If user is Teacher, only the tests created by this teacher is shown
        switch (currUser.getRole()) {
            case "Admin":
                testList = TestService.readAll();
                break;
            case "Teacher":
                testList = TestService.readAll(currUser.getUserId());
                break;
        }
        List<String> testNames = new ArrayList<>();
        for (Test element : testList) {
            testNames.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );
        /*
        List<String> listItems = new ArrayList<>();

        //userList = UserService.readStudents();
        for(User element : userList) {
            listItems.add(element.getFirstName() + " " + element.getLastName());
        }

        ObservableList<String> availableUsers =
                FXCollections.observableArrayList(
                        listItems
                );*/


        //Test Tableview
        testTable = new TableView();
        TableColumn<Table, String> testName = new TableColumn<>("Prov");
        testName.setMinWidth(200);
        testName.setCellValueFactory(new PropertyValueFactory<>("title"));

        testTable.setItems(getTests());
        testTable.getColumns().add(testName);

        //Person tableview
        personTable = new TableView();
        TableColumn anvandareCol = new TableColumn("Användare");
        anvandareCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        personTable.getColumns().add(anvandareCol);
        personTable.setItems(userList);


        //Grupp Tableview
        gruppTable = new TableView();
        TableColumn gruppCol = new TableColumn("Grupp");
        gruppCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));

        gruppTable.getColumns().addAll(gruppCol);
        gruppTable.setItems(groupList);

        deselectPerson();
        deselectTest();
        deselectGrupp();

        shareBtn = new Button("Dela prov");
        mailBtn = new Button("Send mail");
        shareBtn.setOnAction(d->{
            shareTest();
        });
        mailBtn.setOnAction(d->{
            /*int selectedTestIndex = testBox.getSelectionModel().getSelectedIndex();
            Test selectedTest = testList.get(selectedTestIndex);

            int selectedUserIndex = userSelectBox.getSelectionModel().getSelectedIndex();
            selectedUser = userList.get(selectedUserIndex);
            sm.sendmail(selectedUser, selectedTest);*/
        });

        gp.add(testTable, 0, 0);
        gp.add(personTable, 1, 0);
        gp.add(gruppTable, 2, 0);
        gp.add(shareBtn, 3, 1);
        gp.add(mailBtn, 4, 1);
        gp.setPadding(new Insets(150));

        mainPane.getChildren().addAll(menuPane, gp);
        Scene scene = new Scene(mainPane, 1600, 900);
        window.setTitle("Dela prov");
        window.setScene(scene);
        window.show();
    }

    public void  shareTest() {
        //int selectedTestIndex = testBox.getSelectionModel().getSelectedIndex();
        //Test selectedTest = testList.get(selectedTestIndex);

        selectedUser = personTable.getSelectionModel().getSelectedItem();
        selectedGrupp = gruppTable.getSelectionModel().getSelectedItem();
        System.out.print("\n Person "+selectedUser.getUserName()+"\n grupp "+selectedGrupp.getGroupName());

        /*if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till enskild elev") {
            int selectedUserIndex = userSelectBox.getSelectionModel().getSelectedIndex();
            selectedUser = userList.get(selectedUserIndex);
            TestAccessService.create(selectedUser, selectedTest);
        } else if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till grupp") {
            int selectedGroupIndex = userSelectBox.getSelectionModel().getSelectedIndex();
            StudentGroup selectedGroup = groupList.get(selectedGroupIndex);

            List<User> selectedUsers = StudentGroupService.readByGroup(selectedGroup.getStudentGroupId());
            TestAccessService.create(selectedUsers, selectedTest);
        }*/
    }

    public ObservableList<Table> getTests(){
        ObservableList<Table> tests = FXCollections.observableArrayList();
        for(int i = 0; i < testList.size(); i++) {
            tests.addAll(new Table(testList.get(i).gettTitle(), testList.get(i).gettTimeMin(), testList.get(i).gettMaxPoints(),
                    testList.get(i).getUser().getFirstName() + " " + testList.get(i).getUser().getLastName()));
        }
        return tests;
    }

    public void deselectPerson(){
        personTable.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> tableView2) {
                final TableRow<User> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    final int index = row.getIndex();
                    if (index >= 0 && index < personTable.getItems().size() && personTable.getSelectionModel().isSelected(index)  ) {
                        personTable.getSelectionModel().clearSelection();
                        event.consume();
                    }
                });
                return row;
            }
        });
    }

    public void deselectTest(){
        testTable.setRowFactory(new Callback<TableView<Test>, TableRow<Test>>() {
            @Override
            public TableRow<Test> call(TableView<Test> tableView2) {
                final TableRow<Test> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    final int index = row.getIndex();
                    if (index >= 0 && index < testTable.getItems().size() && testTable.getSelectionModel().isSelected(index)  ) {
                        testTable.getSelectionModel().clearSelection();
                        event.consume();
                    }
                });
                return row;
            }
        });
    }

    public void deselectGrupp(){
        gruppTable.setRowFactory(new Callback<TableView<StudentGroup>, TableRow<StudentGroup>>() {
            @Override
            public TableRow<StudentGroup> call(TableView<StudentGroup> tableView2) {
                final TableRow<StudentGroup> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    final int index = row.getIndex();
                    if (index >= 0 && index < gruppTable.getItems().size() && gruppTable.getSelectionModel().isSelected(index)  ) {
                        testTable.getSelectionModel().clearSelection();
                        event.consume();
                    }
                });
                return row;
            }
        });
    }
}
