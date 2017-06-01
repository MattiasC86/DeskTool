package view;

import entity.StudentGroup;
import entity.Test;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import logic.SendMailLogic;
import service.*;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-24.
 */
public class ShareTestView {
    User currUser;
    User selectedUser;

    SendMailLogic sm = new SendMailLogic();
    GridPane gp = new GridPane();

    //ComboBox testBox;
    //ComboBox shareTypeBox;
    //ComboBox userSelectBox;

    TableView testTable;
    TableView personTable;
    TableView grupptable;

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
        TableColumn testCol = new TableColumn("Test");
        testCol.setCellValueFactory(new PropertyValueFactory<>("tTitle"));

        testTable.getColumns().add(testCol);
        testTable.setItems(availableTests);


        //Person tableview
        personTable = new TableView();
        TableColumn anvandareCol = new TableColumn("Användare");
        anvandareCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        personTable.getColumns().add(anvandareCol);
        personTable.setItems(userList);


        //Grupp Tableview
        grupptable = new TableView();
        TableColumn gruppCol = new TableColumn("Grupp");
        gruppCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));

        grupptable.getColumns().add(gruppCol);
        grupptable.setItems(groupList);



        shareBtn = new Button("Dela prov");
        mailBtn = new Button("Send mail");
        shareBtn.setOnAction(d->{
            //shareTest();
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
        gp.add(grupptable, 2, 0);
        gp.add(shareBtn, 3, 1);
        gp.add(mailBtn, 4, 1);
        gp.setPadding(new Insets(150));

        mainPane.getChildren().addAll(menuPane, gp);
        Scene scene = new Scene(mainPane, 1600, 900);
        window.setTitle("Dela prov");
        window.setScene(scene);
        window.show();
    }

    /*public void  shareTest() {
        int selectedTestIndex = testBox.getSelectionModel().getSelectedIndex();
        Test selectedTest = testList.get(selectedTestIndex);

        //selectedUser = persontable.getSelectionModel().getSelectedItem();

        if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till enskild elev") {
            int selectedUserIndex = userSelectBox.getSelectionModel().getSelectedIndex();
            selectedUser = userList.get(selectedUserIndex);
            TestAccessService.create(selectedUser, selectedTest);
        } else if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till grupp") {
            int selectedGroupIndex = userSelectBox.getSelectionModel().getSelectedIndex();
            StudentGroup selectedGroup = groupList.get(selectedGroupIndex);

            List<User> selectedUsers = StudentGroupService.readByGroup(selectedGroup.getStudentGroupId());
            TestAccessService.create(selectedUsers, selectedTest);
        }
    }*/
}
