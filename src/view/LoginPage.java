package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.loginLogic;

/**
 * Created by Rasmus on 2017-05-04.
 */
public class LoginPage {


   public LoginPage(Stage window) {



        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,70));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("Login");
        final Label lblMessage = new Label();

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(lblMessage, 1, 2);


        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);


        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");



        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);
        bp.setId("loginPane");

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp,450,250);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.setTitle("Newton");
        window.setScene(scene);
        window.centerOnScreen();
        window.show();

        loginLogic loginvali = new loginLogic();

        btnLogin.setOnAction(e->{

             if (loginvali.loginValidation(txtUserName.getText(), pf.getText()).equals("Student")) {
                 StartView sv = new StartView();
                 AdminFirstpage afp = new AdminFirstpage(window);
             }

             if (loginvali.loginValidation(txtUserName.getText(), pf.getText()).equals("Lärare")) {
                  StartView sv = new StartView();
                  AdminFirstpage afp = new AdminFirstpage(window);
             }

             if (loginvali.loginValidation(txtUserName.getText(), pf.getText()).equals("Admin")) {
                  StartView sv = new StartView();
                  AdminFirstpage afp = new AdminFirstpage(window);
             }
        });
    }
}


