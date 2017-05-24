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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import service.*;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-24.
 */
public class ShareTestView {
    User currUser;
    FlowPane flowpane = new FlowPane();

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

        Pane menuPane = new Pane();

        Pane mainPane = new Pane();

        MenuBarHelper.getMenuBar(window,menuPane);



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
        flowpane.getChildren().removeAll(userSelectBox, shareTypeBox, shareBtn);
        flowpane.getChildren().addAll(testBox);
        flowpane.relocate(200,100);
        flowpane.setPrefWidth(1200);

        mainPane.getChildren().addAll(menuPane, flowpane);


        Scene scene = new Scene(mainPane, 1600, 900);
        window.setTitle("Dela prov");
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
        flowpane.getChildren().removeAll(userSelectBox, shareTypeBox, shareBtn);
        List<String> shareTypeL = new ArrayList<>();
        shareTypeL.add("Dela till enskild elev");
        shareTypeL.add("Dela till grupp");
        ObservableList<String> shareTypes =
                FXCollections.observableArrayList(
                        shareTypeL
                );
        shareTypeBox = new ComboBox(shareTypes);

        flowpane.getChildren().addAll(shareTypeBox);

        // When type is selected in shareTypeBox
        shareTypeBox.setOnAction(e->{
            displayUserSelectBox();
            //shareBtn = new Button("Dela test");
        });
    }

    public void displayUserSelectBox() {
        flowpane.getChildren().removeAll(userSelectBox, shareBtn);
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

        flowpane.getChildren().addAll(userSelectBox);

        userSelectBox.setOnAction(e->{
            shareBtn = new Button("Dela prov");
            flowpane.getChildren().addAll(shareBtn);
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
