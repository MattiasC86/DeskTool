package view.menuBars;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.AdminFirstpage;
import view.LoginPage;
import view.Settings;
import view.createTest.FxView;
import view.register.Register;

/**
 * Created by Rasmus on 2017-05-11.
 */
public class MenuBarStudent {


    public MenuBarStudent(Pane pane, Stage window) {



        Menu menu1 = new Menu("Hem");

        MenuItem home = new MenuItem("Hem");

        menu1.getItems().addAll(home);

        //skapar en huvudmeny med namn arkiv
        Menu menu2 = new Menu("Arkiv");

        //skapar en submeny med namn test i menyn arkiv
        Menu testMenu = new Menu("Test");

        //skapar menuItems att fylla submenyn Test med
        MenuItem doTest = new MenuItem("Gör Test");
        MenuItem doneTest = new MenuItem("Rättade Test");


        //skapar en huvudmeny med namn Inställningar
        Menu menu3 = new Menu("Inställningar");

        //skapar menuItems som ska fylla menyn Inställningar
        MenuItem editUser = new MenuItem("Redigera användare");

        //Skapar en huvudmeny med namn logga ut
        Menu menu4 = new Menu("Logga ut");

        MenuItem logoutItem = new MenuItem("Logga ut");
        menu4.getItems().addAll(logoutItem);

        menu2.getItems().addAll(testMenu);

        testMenu.getItems().addAll(doTest, doneTest);

        menu3.getItems().addAll(editUser);

        MenuBar menuAdmin = new MenuBar(menu1, menu2, menu3, menu4);
        menuAdmin.setPrefWidth(1600);

        pane.getChildren().addAll(menuAdmin);

        //ActionEvent för loginknappen.
        logoutItem.setOnAction(e -> {
            LoginPage lp = new LoginPage(window);
        });


        //ActionEvent för hemknappen.
        home.setOnAction(e->{
            AdminFirstpage afp = new AdminFirstpage(window);
        });


        editUser.setOnAction(e->{
            Settings st = new Settings(window);
        });

    }

}
