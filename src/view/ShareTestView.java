package view;

import entity.StudentGroup;
import entity.Test;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import logic.LoginLogic;
import service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-24.
 */
public class ShareTestView {
    User currUser;
    FlowPane pane = new FlowPane();

    ComboBox testBox;
    ComboBox shareTypeBox;
    ComboBox userSelectBox;

    Button shareBtn;

    List<Test> testList;
    List<User> userList;
    List<StudentGroup> groupList;

    /*

    -Välj test
        -Dela till elev/grupp
            -Välj specifik elev/grupp
            -Dela

    */

    public ShareTestView(Stage window){
        currUser = UserService.read(LoginLogic.getCurrId());

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
        for(Test element : testList) {
            testNames.add(element.gettTitle());
        }
        ObservableList<String> availableTests =
                FXCollections.observableArrayList(
                        testNames
                );
        testBox = new ComboBox(availableTests);
        pane.getChildren().removeAll(userSelectBox, shareTypeBox, shareBtn);
        pane.getChildren().addAll(testBox);


        Scene scene = new Scene(pane, 1600, 900);
        window.setScene(scene);
        window.show();

        // When a test is selected in testBox
        testBox.setOnAction(e->{
            displayShareTypeBox();
        });



        /*shareBtn.setOnAction(e->{
            shareTest();
        });*/
    }

    public void displayShareTypeBox() {
        pane.getChildren().removeAll(userSelectBox, shareTypeBox, shareBtn);
        List<String> shareTypeL = new ArrayList<>();
        shareTypeL.add("Dela till enskild elev");
        shareTypeL.add("Dela till grupp");
        ObservableList<String> shareTypes =
                FXCollections.observableArrayList(
                        shareTypeL
                );
        shareTypeBox = new ComboBox(shareTypes);

        pane.getChildren().addAll(shareTypeBox);

        // When type is selected in shareTypeBox
        shareTypeBox.setOnAction(e->{
            displayUserSelectBox();
            //shareBtn = new Button("Dela test");
        });
    }

    public void displayUserSelectBox() {
        pane.getChildren().removeAll(userSelectBox, shareBtn);
        List<String> listItems = new ArrayList<>();

        if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till enskild elev") {
            userList = UserService.readStudents();
            for(User element : userList) {
                listItems.add(element.getFirstName() + " " + element.getLastName());
            }
        } else if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till grupp") {
            groupList = StudentGroupService.readAll();
            for(StudentGroup element : groupList) {
                listItems.add(element.getGroupName());
            }
        }

        ObservableList<String> availableUsers =
                FXCollections.observableArrayList(
                        listItems
                );
        userSelectBox = new ComboBox(availableUsers);

        pane.getChildren().addAll(userSelectBox);

        userSelectBox.setOnAction(e->{
            shareBtn = new Button("Dela prov");
            pane.getChildren().addAll(shareBtn);
            shareBtn.setOnAction(d->{
                shareTest();
            });
        });
    }


    public void  shareTest() {
        int selectedTestIndex = testBox.getSelectionModel().getSelectedIndex();
        Test selectedTest = testList.get(selectedTestIndex);

        if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till enskild elev") {
            int selectedUserIndex = userSelectBox.getSelectionModel().getSelectedIndex();
            User selectedUser = userList.get(selectedUserIndex);
            TestAccessService.create(selectedUser, selectedTest);
        } else if(shareTypeBox.getSelectionModel().getSelectedItem() == "Dela till grupp") {
            int selectedGroupIndex = userSelectBox.getSelectionModel().getSelectedIndex();
            StudentGroup selectedGroup = groupList.get(selectedGroupIndex);

            List<User> selectedUsers = StudentGroupService.readByGroup(selectedGroup.getStudentGroupId());
            TestAccessService.create(selectedUsers, selectedTest);
        }
    }
}
