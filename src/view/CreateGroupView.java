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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.LoginLogic;
import service.GroupDetailsService;
import service.StudentGroupService;
import service.TestAccessService;
import service.UserService;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;


public class CreateGroupView  {
    Pane pane = new Pane();
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
    Button btnReviewMembers;

    Label lblSelectUser;
    Label lblSelectedUsers;
    Label lblGroups;
    Label lblGroupName;


    public CreateGroupView(Stage window){

        Pane mainPane = new Pane();

        pane.setPrefSize(1200, 700);
        pane.relocate(200,100);


        MenuBarHelper.getMenuBar(window, mainPane);

        allUsers = UserService.readStudents();
        selectedUsers = new ArrayList<>();

        lblSelectUser = new Label("Välj användare att lägga till");
        lblSelectedUsers = new Label("Valda användare");
        lblGroups = new Label("Befintliga grupper");
        lblGroupName = new Label("Välj gruppnamn");

        groupNameInput = new TextField();
        groupNameInput.setPromptText("Fyll i namn på gruppen");

        // Creating ListView containing all students
        userListView = new ListView<>();
        allUsersObsList = FXCollections.observableArrayList();
            for(User user : allUsers){
                allUsersObsList.add(user.getFirstName() + " " + user.getLastName());
            }
        userListView.setItems(allUsersObsList);

        // Creating ListView for selected users
        selUsersObsList = FXCollections.observableArrayList();
        selUsersListView = new ListView<>(selUsersObsList);

        // Creating ListView containing all existing groups
        allGroups = StudentGroupService.readAll();
        groupsListView = new ListView<>();
        groupsObsList = FXCollections.observableArrayList();
        for(StudentGroup group : allGroups){
            groupsObsList.add(group.getGroupName());
        }
        groupsListView.setItems(groupsObsList);

        // Button creations
        btnAdd = new Button(">>");
        btnRemove = new Button("<<");
        btnCreateGroup = new Button("SPARA GRUPP");
        btnRemoveGroup = new Button("Radera grupp");
        btnReviewMembers = new Button("Se gruppmedlemmar");

        // Node relocations in pane
        lblSelectUser.relocate(100, 50);
        lblSelectedUsers.relocate(420, 50);
        lblGroups.relocate(750, 50);

        userListView.relocate(100, 100);
        selUsersListView.relocate(420, 100);
        groupsListView.relocate(750,100);

        btnAdd.relocate(365, 250);
        btnRemove.relocate(365, 300);

        btnRemoveGroup.relocate(730, 520);
        btnReviewMembers.relocate(860, 520);


        lblGroupName.relocate(100, 520);
        groupNameInput.relocate(100, 550);
        btnCreateGroup.relocate(100,600);

        pane.getChildren().addAll(groupNameInput, userListView, selUsersListView, btnAdd, btnRemove, groupsListView,
                btnCreateGroup, btnRemoveGroup, btnReviewMembers, lblSelectUser, lblSelectedUsers, lblGroups, lblGroupName);

        mainPane.getChildren().addAll(pane);

        mainPane.setId("settingsBk");
        pane.setId("settingsPane");

        Scene scene = new Scene(mainPane, 1600, 900);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(scene);
        window.show();

        // Button listeners
        btnAdd.setOnAction(e -> addUser());
        btnRemove.setOnAction(e -> removeUser());
        btnCreateGroup.setOnAction(e -> createGroup());
        btnRemoveGroup.setOnAction(e -> removeGroup());
        btnReviewMembers.setOnAction(e -> reviewMembers());
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

    // Saves the new group in database unless groupname is taken
    public void createGroup() {
        boolean validated = true;
        for(StudentGroup group : allGroups) {
            if(group.getGroupName().equals(groupNameInput.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Gruppnamn upptaget.");
                alert.setContentText("Vänligen fyll i ett nytt gruppnamn");
                validated = false;
                alert.showAndWait();
            }
        }
        if(groupNameInput.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fält för gruppnamn är tomt.");
            alert.setContentText("Vänligen fyll i ett  gruppnamn.");
            alert.showAndWait();
            validated = false;
        } else if(selectedUsers.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inga medlemmar valda.");
            alert.setContentText("Vänligen välj minst en medlem för gruppen.");
            alert.showAndWait();
            validated = false;
        }

        if(validated) {
            StudentGroup studentGroup = new StudentGroup(groupNameInput.getText());

            StudentGroupService.create(studentGroup);
            for(User user : selectedUsers) {
                GroupDetailsService.create(new GroupDetails(studentGroup, user));
            }
            groupsListView.getItems().add(studentGroup.getGroupName());
            allGroups.add(studentGroup);
        }
    }

    //Removes selected group from database
    public void removeGroup() {
        int index = groupsListView.getSelectionModel().getSelectedIndex();
        StudentGroupService.delete(allGroups.get(index));
        groupsListView.getItems().remove(index);
        allGroups.remove(index);

    }

    public void reviewMembers() {
        int index = groupsListView.getSelectionModel().getSelectedIndex();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(groupsListView.getItems().get(index));
        alert.setHeaderText("Medlemmar i " + groupsListView.getItems().get(index) + ":");

        List<User> members = StudentGroupService.readByGroup(allGroups.get(index).getStudentGroupId());
        String membersList = "";
        for(User user : members) {
            membersList += user.getFirstName() + " " + user.getLastName() + "\n";
        }

        alert.setContentText(membersList);

        alert.showAndWait();
    }

}
