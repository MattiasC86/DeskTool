package view.register;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

        TextField texfieldFirstname = new TextField();
        TextField textFieldLastname = new TextField();
        TextField textFieldUsername = new TextField();
        TextField textFieldEmail = new TextField();
        TextField textFieldPassword = new TextField();

        Button registerButton = new Button("Registrera");


        gp.add(firstname, 0 ,0);
        gp.add(texfieldFirstname, 1 ,0);

        gp.add(lastname, 0 ,1);
        gp.add(textFieldLastname, 1 ,1);

        gp.add(username, 0 ,2);
        gp.add(textFieldUsername, 1 ,2);

        gp.add(email, 0 ,3);
        gp.add(textFieldEmail, 1 ,3);

        gp.add(password, 0 ,4);
        gp.add(textFieldPassword, 1 ,4);

        gp.add(registerButton,0,5);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        Text text = new Text("Registrera användare");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        hb.getChildren().addAll(text);

        bp.setTop(text);
        bp.setCenter(gp);

        BorderPane mainBorderPane = new BorderPane();

        mainBorderPane.setTop(pane);
        mainBorderPane.setCenter(bp);





        Scene welcomeScene = new Scene(mainBorderPane, 1600,900);
        window.setScene(welcomeScene);
        window.centerOnScreen();



    }
}
