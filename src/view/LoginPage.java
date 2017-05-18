package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.LoginLogic;
import view.homepage.AdminFirstpage;
import view.homepage.StudentFirstpage;
import view.homepage.TeacherFirstpage;

/**
 * Created by Rasmus on 2017-05-04.
 */
public class LoginPage {


   public LoginPage(Stage window) {

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(40,50,100,50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,70));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Användarnamn");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Lösenord");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("Logga in");
        final Label errorMessage = new Label();
        errorMessage.setFont(Font.font("Courier New", FontWeight.BOLD, 12));

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(errorMessage, 1, 2);


        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);


        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        errorMessage.setId("errorMessageLogin");
        bp.setId("loginPane");
        btnLogin.setDefaultButton(true);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp,500,250);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        window.sizeToScene();
        window.setTitle("Newton");
        window.setScene(scene);
        window.centerOnScreen();
        window.show();


        //ActionEvent som körs när man trycker på loginknappen.
        btnLogin.setOnAction(e->{
             Platform.runLater(() -> {
                  int statusCode = LoginLogic.loginValidation(txtUserName.getText(), pf.getText());
                  if (statusCode == 0) {
                       errorMessage.setText("Fel användarnamn/lösenord!");
                  } else if (statusCode == 1){
                       //Elev start page
                       StudentFirstpage sfp = new StudentFirstpage(window);
                  }
                  else if (statusCode == 2){
                       //Lärare start page
                       TeacherFirstpage tfp = new TeacherFirstpage(window);
                  }
                  else if (statusCode == 3){
                       //Admin start page
                       AdminFirstpage afp = new AdminFirstpage(window);
                  }
             });
        });
    }
}


