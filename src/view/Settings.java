package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by Rasmus on 2017-05-11.
 **/

public class Settings {

            public Settings(Stage window) {

                Pane pane = new Pane();

                MenuBarAdmin x = new MenuBarAdmin(pane, window);

                BorderPane bp = new BorderPane();
                bp.setPadding(new Insets(10, 50, 50, 50));

                bp.setTop(pane);

                Label labelFirstname = new Label("Förnamn: ");
                Label currentFirstname = new Label();

                Label labelLastname = new Label("Efternamn: ");
                Label currentLastname = new Label();

                Label labelEmail = new Label("Email: ");
                Label currentEmail = new Label();

                Label labelUsername = new Label("Användarnamn: ");
                Label currentUsername = new Label();

                Label labelPassword = new Label("Lösenord: ");
                Label currentPassword = new Label();

                TextField changeEmail = new TextField();
                TextField changePassword = new TextField();

                Button buttonChangeEmail = new Button("Change email");
                Button buttonChangePassword = new Button("Change password");

                GridPane gp = new GridPane();

                gp.add(labelFirstname, 0, 0);
                gp.add(currentFirstname, 1, 0);

                gp.add(labelLastname, 0, 1);
                gp.add(currentLastname, 1, 1);

                gp.add(labelUsername, 0, 2);
                gp.add(currentUsername, 1, 2);

                gp.add(labelEmail, 0, 3);
                gp.add(currentEmail, 1, 3);
            }
}
