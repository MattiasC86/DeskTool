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
import view.MenuBarAdmin;


/**
 * Created by Rasmus on 2017-05-09.
 */
public class Register {

    public Register(Stage window){

        Pane pane = new Pane();

        MenuBarAdmin x = new MenuBarAdmin(pane, window);

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));

        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,70));

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20, 20, 20, 20));
        gp.setHgap(5);
        gp.setVgap(5);

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

        Label verifyText = new Label();
        verifyText.relocate(730, 350);

        Button registerButton = new Button("Registrera");

        ChoiceBox privilege = new ChoiceBox();
        privilege.setItems(FXCollections.observableArrayList("Student", "Lärare", "Admin"));
        privilege.setValue("Student");


        gp.add(firstname, 0 ,0);
        gp.add(textFieldFirstname, 1 ,0);

        gp.add(lastname, 0 ,1);
        gp.add(textFieldLastname, 1 ,1);

        gp.add(username, 0 ,2);
        gp.add(textFieldUsername, 1 ,2);

        gp.add(email, 0 ,3);
        gp.add(textFieldEmail, 1 ,3);

        gp.add(password, 0 ,4);
        gp.add(textFieldPassword, 1 ,4);

        gp.add(registerButton,0,5);
        gp.add(privilege, 1,5);


        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        Text text = new Text("Registrera användare");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        hb.getChildren().addAll(text);

        bp.setTop(text);
        bp.setCenter(gp);

        Pane mainBorderPane = new Pane();
        bp.relocate(600,50);

        mainBorderPane.getChildren().addAll(pane,bp,verifyText);

        textFieldUsername.setId("errorPromt");
        textFieldEmail.setId("errorPromt");
        verifyText.setId("verifyText");


        Scene welcomeScene = new Scene(mainBorderPane, 1600,900);
        welcomeScene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setScene(welcomeScene);
        window.centerOnScreen();

        
        registerButton.setOnAction(e->{
            int result = RegisterLogic.registerUser(textFieldFirstname.getText(), textFieldLastname.getText(), textFieldUsername.getText(),
                    textFieldEmail.getText(), textFieldPassword.getText(), privilege.getSelectionModel().getSelectedItem().toString());
            switch(result) {
                case 1:
                    //username upptagen
                    textFieldUsername.setText("");
                    textFieldUsername.setPromptText("Upptaget användarnamn!");

                    break;
                case 2:
                    //email upptagen
                    textFieldEmail.setText("");
                    textFieldEmail.setPromptText("Upptagen email!");
                    break;
                case 3:
                    //användaren har skapats
                    textFieldFirstname.setText("");
                    textFieldLastname.setText("");
                    textFieldUsername.setText("");
                    textFieldEmail.setText("");
                    textFieldPassword.setText("");
                    verifyText.setText("Användaren är registrerad!");
                    break;
            }

        });

    }
}
