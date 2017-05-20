package view.register;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RegisterLogic;
import view.menuBars.MenuBarAdmin;


/**
 * Created by Rasmus on 2017-05-09.
 */
public class Register {

    public Register(Stage window){

        Pane pane = new Pane();

        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(20,50,50,50));

        Pane gp = new Pane();


        Label firstname = new Label("Förnamn:");
        Label lastname = new Label("Efternamn:");
        Label username = new Label("Användarnamn:");
        Label email = new Label("Email:");
        Label password = new Label("Lösenord:");

        TextField textFieldFirstname = new TextField();
        TextField textFieldLastname = new TextField();
        TextField textFieldUsername = new TextField();
        TextField textFieldEmail = new TextField();
        TextField textFieldPassword = new TextField();

        Label verifyText = new Label("");
        Label emptyFields = new Label("");
        Button registerButton = new Button("Registrera");

        ChoiceBox privilege = new ChoiceBox();
        privilege.setItems(FXCollections.observableArrayList("Student", "Lärare", "Admin"));
        privilege.setValue("Student");



        firstname.relocate(350,100);
        textFieldFirstname.relocate(500,100);
        textFieldFirstname.setPrefWidth(250);

        lastname.relocate(350,150);
        textFieldLastname.relocate(500, 150);
        textFieldLastname.setPrefWidth(250);

        username.relocate(350,200);
        textFieldUsername.relocate(500, 200);
        textFieldUsername.setPrefWidth(250);

        email.relocate(350, 250);
        textFieldEmail.relocate(500,250);
        textFieldEmail.setPrefWidth(250);

        password.relocate(350, 300);
        textFieldPassword.relocate(500,300);
        textFieldPassword.setPrefWidth(250);

        registerButton.relocate(650,350);
        privilege.relocate(500,350);
        verifyText.relocate(780,460);
        emptyFields.relocate(780,460);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        Text text = new Text("Registrera användare");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        text.relocate(380,40);


        gp.getChildren().addAll(text,firstname, lastname, username, email, password, textFieldFirstname, textFieldLastname,
                textFieldUsername, textFieldEmail, textFieldPassword, registerButton, privilege);


        bp.setCenter(gp);

        Pane mainPane = new Pane();
        bp.relocate(200,50);
        bp.setPrefWidth(1200);
        bp.setPrefHeight(500);

        mainPane.getChildren().addAll(pane,bp,verifyText,emptyFields);

        textFieldUsername.setId("errorPromt");
        textFieldEmail.setId("errorPromt");
        verifyText.setId("verifyText");
        gp.setId("rootRegister");
        firstname.setId("registerLabels");
        lastname.setId("registerLabels");
        username.setId("registerLabels");
        email.setId("registerLabels");
        password.setId("registerLabels");
        mainPane.setId("registerBackground");
        registerButton.setId("btnRegister");
        emptyFields.setId("emptyFields");






        Scene welcomeScene = new Scene(mainPane, 1600,900);
        welcomeScene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(welcomeScene);
        window.setTitle("Skapa användare");
        window.centerOnScreen();


        registerButton.setOnAction(e->{
            int result = RegisterLogic.registerUser(textFieldFirstname.getText(), textFieldLastname.getText(), textFieldUsername.getText(),
                    textFieldEmail.getText(), textFieldPassword.getText(), privilege.getSelectionModel().getSelectedItem().toString());
            switch(result) {
                case 0:
                    //Om något fält är tomt
                    verifyText.setText("");
                    emptyFields.setText("Fyll i alla tomma fält!");
                    break;

                case 1:
                    //username upptagen
                    textFieldUsername.setText("");
                    textFieldUsername.setPromptText("Upptaget användarnamn!");
                    emptyFields.setText("");
                    verifyText.setText("");


                    break;
                case 2:
                    //email upptagen
                    textFieldEmail.setText("");
                    textFieldEmail.setPromptText("Upptagen email!");
                    verifyText.setText("");
                    emptyFields.setText("");
                    break;
                case 3:
                    //användaren har skapats
                    textFieldFirstname.setText("");
                    textFieldLastname.setText("");
                    textFieldUsername.setText("");
                    textFieldEmail.setText("");
                    textFieldPassword.setText("");
                    emptyFields.setText("");
                    verifyText.setText("Användaren är registrerad!");
                    break;
            }

        });

    }
}
