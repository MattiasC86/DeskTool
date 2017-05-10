package view;

import javafx.application.Application;
import javafx.stage.Stage;
import view.createTest.FxView;
import view.register.Register;

/**
 * Created by Rasmus on 2017-05-03.
 */
public class StartView extends Application{
    Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        LoginPage startView = new LoginPage(window);


    }
    public static void main(String[] args) {
        launch(args);
    }



    public void goToHomepage(Stage window){ AdminFirstpage afp = new AdminFirstpage(window); }

    public void logout (Stage window){
        LoginPage sv = new LoginPage(window);
    }

    public void registerATeacher(Stage window){Register rt = new Register(window); }

    public void goToCreateTest(Stage window) {FxView fv = new FxView(window); }

}
