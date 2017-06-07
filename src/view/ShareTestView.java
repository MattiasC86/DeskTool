package view;

import entity.StudentGroup;
import entity.Test;
import entity.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import logic.SendMailLogic;
import service.*;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

public class ShareTestView {

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

    public ShareTestView(Stage window) {
        currUser = UserService.read(LoginLogic.getCurrId());

        Pane menuPane = new Pane();
        MenuBarHelper.getMenuBar(window, menuPane);
        Pane mainPane = new Pane();

        selector = "";
        lChosenTest = new Label("Valt test: ");
        lChosenTarget = new Label("Dela till: ");

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
            sendMail();
        });


        testTable.relocate(150,200);
        userTable.relocate(400,200);
        groupTable.relocate(650, 200);
        shareBtn.relocate(920,575);
        mailBtn.relocate(1000, 575);
        lChosenTest.relocate(920, 555);
        lChosenTarget.relocate(1070,555);
        menuPane.relocate(0,0);

        mainPane.getChildren().addAll(testTable, userTable, groupTable, shareBtn, mailBtn, lChosenTest, lChosenTarget, menuPane);

        Scene scene = new Scene(mainPane, 1600, 900);
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

    public void sendMail(){
        switch(selector) {
            case "User":
                System.out.println("Elev " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
                sm.sendmail(selectedUser, selectedTest);
                break;
            case "Group":
                System.out.println("Gruppen " + selectedGroup.getGroupName());
                List<User> selectedUsers = StudentGroupService.readByGroup(selectedGroup.getStudentGroupId());
                sm.sendMulti(selectedUsers, selectedTest);
                break;
        }
    }
}