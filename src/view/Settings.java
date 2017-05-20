package view;

import entity.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.LoginLogic;
import service.UserService;
import view.menuBars.MenuBarAdmin;
import view.menuBars.MenuBarHelper;


/**
 * Created by Rasmus on 2017-05-11.
 **/

public class Settings {

    public Settings(Stage window) {

        User user = UserService.read(LoginLogic.getCurrId());

        Pane pane = new Pane();

        MenuBarHelper.getMenuBar(window, pane);

        Pane bp = new Pane();
        bp.setPadding(new Insets(20, 50, 50, 50));

        Pane labelPane = new Pane();


        Label labelFirstname = new Label("Förnamn: ");
        Label currentFirstname = new Label(user.getFirstName());

        Label labelLastname = new Label("Efternamn: ");
        Label currentLastname = new Label(user.getLastName());

        Label labelEmail = new Label("Email: " );
        Label currentEmail = new Label(user.getEmail());

        Label labelUsername = new Label("Användarnamn: ");
        Label currentUsername = new Label(user.getUserName());

        Label labelPassword = new Label("Lösenord: " );
        Label currentPassword = new Label(user.getPassword());

        Label newEmail = new Label("Ny email: ");
        Label newPassword = new Label("Nytt lösenord: ");

        TextField changeEmail = new TextField();
        TextField changePassword = new TextField();

        Button buttonChangeEmail = new Button("OK");
        Button buttonChangePassword = new Button("OK");

        Text text = new Text("Redigera användare");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setFill(Color.WHITE);


        labelFirstname.relocate(400, 100);
        currentFirstname.relocate(550, 100);

        labelLastname.relocate(400,150);
        currentLastname.relocate(550, 150);

        labelUsername.relocate(400,200);
        currentUsername.relocate(550, 200);

        labelEmail.relocate(400, 250);
        currentEmail.relocate(550,250);

        labelPassword.relocate(400, 300);
        currentPassword.relocate(550,300);

        newEmail.relocate(400,350);
        changeEmail.relocate(550,350);
        changeEmail.setPrefWidth(200);
        buttonChangeEmail.relocate(770, 350);
        buttonChangeEmail.setPrefWidth(50);

        newPassword.relocate(400,400);
        changePassword.relocate(550,400);
        changePassword.setPrefWidth(200);
        buttonChangePassword.relocate(770, 400);
        buttonChangePassword.setPrefWidth(50);

        text.relocate(450,40);

        labelPane.getChildren().addAll(labelFirstname,currentFirstname,labelLastname,currentLastname,labelUsername,currentUsername,labelEmail,currentEmail,labelPassword,currentPassword,
                newEmail,changeEmail,buttonChangeEmail,newPassword,changePassword,buttonChangePassword,text);

        bp.getChildren().addAll(labelPane);

        Rectangle rect = new Rectangle(1200,600);
        rect.setArcHeight(100.0);
        rect.setArcWidth(100.0);

        bp.setClip(rect);

        Pane mainPane = new Pane();
        bp.relocate(200,100);
        bp.setPrefWidth(1200);
        bp.setPrefHeight(600);

        mainPane.getChildren().addAll(bp,pane);

        bp.setId("settingsPane");
        mainPane.setId("settingsBk");
        labelFirstname.setId("settingsLabel");
        labelLastname.setId("settingsLabel");
        labelEmail.setId("settingsLabel");
        labelUsername.setId("settingsLabel");
        labelPassword.setId("settingsLabel");
        newEmail.setId("settingsLabel");
        newPassword.setId("settingsLabel");
        currentFirstname.setId("settingsLabel");
        currentLastname.setId("settingsLabel");
        currentEmail.setId("settingsLabel");
        currentPassword.setId("settingsLabel");
        currentUsername.setId("settingsLabel");





        Scene scene = new Scene(mainPane, 1600, 900);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(scene);
        window.setTitle("Inställnigar");

        // Email is updated for user
        buttonChangeEmail.setOnAction(e->{
            if(!changeEmail.getText().equalsIgnoreCase("")) {
                UserService.update(user.getUserId(), "email", changeEmail.getText());
                user.setEmail(changeEmail.getText());
                currentEmail.setText(user.getEmail());
                changeEmail.setText("");
            }
        });

        // Password is updated for user
        buttonChangePassword.setOnAction(e->{
            if (!changePassword.getText().equalsIgnoreCase("")) {
                UserService.update(user.getUserId(), "password", changePassword.getText());
                user.setPassword(changePassword.getText());
                currentPassword.setText(user.getPassword());
                changePassword.setText("");
            }
        });

    }
}
