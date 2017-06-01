package view;

import entity.GroupDetails;
import entity.StudentGroup;
import entity.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.GroupDetailsService;
import service.StudentGroupService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-24.
 */
public class CreateGroupView extends Application {
    FlowPane pane = new FlowPane();
    List<User> allUsers;
    List<User> selectedUsers;
    List<StudentGroup> allGroups;

    ObservableList<String> allUsersObsList;
    ObservableList<String> selUsersObsList;
    ObservableList<String> groupsObsList;

    ListView<String> userListView;
    ListView<String> selUsersListView;
    ListView<String> groupsListView;

    TextField groupNameInput;

    Button btnAdd;
    Button btnRemove;
    Button btnCreateGroup;
    Button btnRemoveGroup;

    @Override
    public void start(Stage primaryStage) throws Exception {

        allUsers = UserService.readAll();
        selectedUsers = new ArrayList<>();

        groupNameInput = new TextField();
        groupNameInput.setPromptText("Fyll i namn på gruppen");

        // Creating ListView containing all existing groups
        allGroups = StudentGroupService.readAll();
        groupsListView = new ListView<>();
        groupsObsList = FXCollections.observableArrayList();
        for(StudentGroup group : allGroups){
            groupsObsList.add(group.getGroupName());
        }
        groupsListView.setItems(groupsObsList);

        // Creating ListView containing all users
        userListView = new ListView<>();
        allUsersObsList = FXCollections.observableArrayList();
            for(User user : allUsers){
                allUsersObsList.add(user.getFirstName() + " " + user.getLastName());
            }
        userListView.setItems(allUsersObsList);

        // Creating ListView for selected users
        selUsersObsList = FXCollections.observableArrayList();
        selUsersListView = new ListView<>(selUsersObsList);

        btnAdd = new Button("Lägg till elev");
        btnRemove = new Button("Ta bort elev");
        btnCreateGroup = new Button("Skapa grupp");
        btnRemoveGroup = new Button("Radera grupp");

        pane.getChildren().addAll(groupNameInput, userListView, selUsersListView, btnAdd, btnRemove, groupsListView,
                btnCreateGroup, btnRemoveGroup);


        Scene scene = new Scene(pane, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();



        btnAdd.setOnAction(e -> addUser());
        btnRemove.setOnAction(e -> removeUser());
        btnCreateGroup.setOnAction(e -> createGroup());
        btnRemoveGroup.setOnAction(e -> removeGroup());

        //NÄR MAN TRYCKER PÅ KNAPPEN "SPARA GRUPP" --->
        //StudentGroupService.create(groupName, selectedUsers);
    }

    public void addUser() {
        int index = userListView.getSelectionModel().getSelectedIndex();
        selectedUsers.add(allUsers.get(index));
        allUsers.remove(index);

        selUsersListView.getItems().add(userListView.getItems().get(index));
        userListView.getItems().remove(index);

        System.out.println("ALL USERS:");
        for(User user : allUsers) {
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
        System.out.println("SELECTED USERS:");
        for(User user : selectedUsers) {
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
    }

    public void removeUser() {
        int index = selUsersListView.getSelectionModel().getSelectedIndex();
        allUsers.add(selectedUsers.get(index));
        selectedUsers.remove(index);

        userListView.getItems().add(selUsersListView.getItems().get(index));
        selUsersListView.getItems().remove(index);

        System.out.println("ALL USERS:");
        for(User user : allUsers) {
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
        System.out.println("SELECTED USERS:");
        for(User user : selectedUsers) {
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
    }

    public void createGroup() {
        StudentGroup studentGroup = new StudentGroup(groupNameInput.getText());

        StudentGroupService.create(studentGroup);
        for(User user : selectedUsers) {
            GroupDetailsService.create(new GroupDetails(studentGroup, user));
        }
        groupsListView.getItems().add(studentGroup.getGroupName());
        allGroups.add(studentGroup);
    }

    public void removeGroup() {
        int index = groupsListView.getSelectionModel().getSelectedIndex();
        StudentGroupService.delete(allGroups.get(index));
        groupsListView.getItems().remove(index);
        allGroups.remove(index);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
