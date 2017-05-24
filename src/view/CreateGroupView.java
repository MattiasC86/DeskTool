package view;

import entity.User;
import javafx.application.Application;
import javafx.stage.Stage;
import service.StudentGroupService;
import service.UserService;

import java.util.List;

/**
 * Created by matti on 2017-05-24.
 */
public class CreateGroupView extends Application {
    List<User> allUsers;
    List<User> selectedUsers;

    String groupName = "";


    @Override
    public void start(Stage primaryStage) throws Exception {
        allUsers = UserService.readAll();


        //NÄR MAN TRYCKER PÅ KNAPPEN "SPARA GRUPP" --->
        StudentGroupService.create(groupName, selectedUsers);
    }
}
