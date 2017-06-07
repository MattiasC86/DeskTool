package view;

import entity.StudentGroup;
import entity.Test;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.LoginLogic;
import logic.SendMailLogic;
import service.*;
import view.homepage.Table;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

public class ShareTestView {
    FlowPane pane = new FlowPane();

    User currUser;
    User selectedUser;
    Test selectedTest;
    StudentGroup selectedGroup;

    List<Test> testList;
    List<User> userList;
    List<StudentGroup> groupList;

    ListView<String> testTable;
    ListView<String> userTable;
    ListView<String> groupTable;

    String selector;

    Label lChosenTest;
    Label lChosenTarget;

    Button shareBtn;
    Button mailBtn;

    SendMailLogic sm = new SendMailLogic();
    Rectangle rect;
    Object val;
    int intval;

    public ShareTestView(Stage window) {
        currUser = UserService.read(LoginLogic.getCurrId());

        Pane menuPane = new Pane();
        MenuBarHelper.getMenuBar(window, menuPane);
        Pane mainPane = new Pane();

        selector = "";
        lChosenTest = new Label("Valt test: ");
        lChosenTarget = new Label("Dela till: ");

        //Empty space
        rect = new Rectangle(500, 250);
        rect.setFill(Color.TRANSPARENT);

        // Test ListVIew
        // If logged in user is Admin, all tests are shown
        // If user is Teacher, only the tests created by this teacher is shown
        testList = new ArrayList<>();
        switch (currUser.getRole()) {
            case "Admin":
                testList = TestService.readAll();
                break;
            case "Teacher":
                testList = TestService.readAll(currUser.getUserId());
                break;
        }
        testTable = new ListView<>();
        List<String> testListNames = new ArrayList<>();
        for(Test test : testList) {
            testListNames.add(test.gettTitle());
        }
        testTable.getItems().addAll(testListNames);

        //Person ListVIew
        userList = UserService.readStudents();
        userTable = new ListView<>();
        List<String> userListNames = new ArrayList<>();
        for(User user : userList) {
            userListNames.add(user.getFirstName() + " " + user.getLastName());
        }
        userTable.getItems().addAll(userListNames);

        //Group ListVIew
        groupList = StudentGroupService.readAll();
        groupTable = new ListView<>();
        List<String> groupListNames = new ArrayList<>();
        for(StudentGroup group: groupList) {
            groupListNames.add(group.getGroupName());
        }
        groupTable.getItems().addAll(groupListNames);

        shareBtn = new Button("Dela prov");
        shareBtn.setDisable(true);  // Disabled until both a test and a student or group has been selected
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

        pane.getChildren().addAll(testTable, userTable, groupTable, shareBtn, mailBtn, lChosenTest, lChosenTarget);

        Scene scene = new Scene(pane, 1600, 900);
        window.setTitle("Dela prov");
        window.setScene(scene);
        window.show();

        // Variables are set when items are selected in the tables
        testTable.setOnMouseClicked(event -> {
            int selIndex = testTable.getSelectionModel().getSelectedIndex();
            lChosenTest.setText("Valt test: " + testTable.getItems().get(selIndex));
            selectedTest = testList.get(selIndex);
            if(!lChosenTarget.getText().equals("Dela till: ")) {
                System.out.println("WHAT");
                shareBtn.setDisable(false);
            }
        });

        userTable.setOnMouseClicked(event -> {
            int selIndex = userTable.getSelectionModel().getSelectedIndex();
            lChosenTarget.setText("Dela till: " + userTable.getItems().get(selIndex));
            selectedUser = userList.get(selIndex);
            selector = "User";
            groupTable.getSelectionModel().clearSelection();
            if(!lChosenTest.getText().equals("Valt test: ")) {
                shareBtn.setDisable(false);
            }
        });

        groupTable.setOnMouseClicked(event -> {
            int selIndex = groupTable.getSelectionModel().getSelectedIndex();
            lChosenTarget.setText("Dela till: " + groupTable.getItems().get(selIndex));
            selectedGroup = groupList.get(selIndex);
            userTable.getSelectionModel().clearSelection();
            selector = "Group";
            if(!lChosenTest.getText().equals("Valt test: ")) {
                shareBtn.setDisable(false);
            }
        });
    }

    public void  shareTest() {
        System.out.println("DELAR " + selectedTest.gettTitle() + "TILL: ");
        switch(selector) {
            case "User":
                System.out.println("Elev " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
                TestAccessService.create(selectedUser, selectedTest);
                break;
            case "Group":
                System.out.println("Gruppen " + selectedGroup.getGroupName());
                List<User> selectedUsers = StudentGroupService.readByGroup(selectedGroup.getStudentGroupId());
                TestAccessService.create(selectedUsers, selectedTest);
                break;
        }
    }
}