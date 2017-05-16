package view;

import entity.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import service.UserService;
import view.menuBars.MenuBarAdmin;


/**
 * Created by Rasmus on 2017-05-11.
 **/

public class Settings {

    public Settings(Stage window) {

        User user = UserService.read(LoginLogic.getCurrId());

        Pane pane = new Pane();

        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        BorderPane bp = new BorderPane();


        bp.setTop(pane);

        Label labelFirstname = new Label("Förnamn: " + user.getFirstName());
        Label currentFirstname = new Label();

        Label labelLastname = new Label("Efternamn: " + user.getLastName());
        Label currentLastname = new Label();

        Label labelEmail = new Label("Email: " + user.getEmail());
        Label currentEmail = new Label();

        Label labelUsername = new Label("Användarnamn: " + user.getUserName());
        Label currentUsername = new Label();

        Label labelPassword = new Label("Lösenord: " + user.getPassword());
        Label currentPassword = new Label();

        Label newEmail = new Label("Ny email: ");
        Label newPassword = new Label("Nytt lösenord: ");

        TextField changeEmail = new TextField();
        TextField changePassword = new TextField();

        Button buttonChangeEmail = new Button("OK");
        Button buttonChangePassword = new Button("OK");

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20,20,20,20));
        gp.setVgap(10);
        gp.setHgap(10);

        gp.add(labelFirstname, 0, 0);
        gp.add(currentFirstname, 1, 0);

        gp.add(labelLastname, 0, 1);
        gp.add(currentLastname, 1, 1);

        gp.add(labelUsername, 0, 2);
        gp.add(currentUsername, 1, 2);

        gp.add(labelEmail, 0, 3);
        gp.add(currentEmail, 1, 3);

        gp.add(labelPassword, 0, 4);
        gp.add(currentPassword,1,4);

        gp.add(newEmail,0,5);
        gp.add(changeEmail, 1, 5);
        gp.add(buttonChangeEmail, 2, 5);

        gp.add(newPassword, 0, 6);
        gp.add(changePassword, 1, 6);
        gp.add(buttonChangePassword,2,6);

        bp.setCenter(gp);

        Scene scene = new Scene(bp, 1600, 900);
        window.setScene(scene);
        window.setTitle("Inställnigar");

        // Email is updated for user
        buttonChangeEmail.setOnAction(e->{
            UserService.update(user.getUserId(), "email", changeEmail.getText());
            user.setEmail(changeEmail.getText());
            labelEmail.setText("Email: " + user.getEmail());
        });

        // Password is updated for user
        buttonChangePassword.setOnAction(e->{
            UserService.update(user.getUserId(), "password", changePassword.getText());
            user.setPassword(changePassword.getText());
            labelPassword.setText("Lösenord: " + user.getPassword());
        });

    }
}
