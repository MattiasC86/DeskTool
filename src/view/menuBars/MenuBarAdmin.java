package view.menuBars;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.ShareTestView;
import view.statistics.StatisticsView;
import view.doTest.SelectTestView;
import view.homepage.AdminFirstpage;
import view.LoginPage;
import view.Settings;
import view.createTest.FxView;
import view.register.Register;

/**
 * Created by Rasmus on 2017-05-03.
 */


public class MenuBarAdmin {


    public MenuBarAdmin(Pane pane, Stage window){

        //Creates a menu thats used to get to the firstpage
        Menu home = new Menu("Hem");

        //Creates a menu for tests and users.
        Menu arkivMenu = new Menu("Arkiv");

        //Creates a submenu for the menu arkivMenu that contains everthings for tests
        Menu testMenu = new Menu("Test");

        //Creates MenuItems for testMenu
        MenuItem createTest = new MenuItem("Skapa Test");
        MenuItem shareTest = new MenuItem("Dela Test");
        MenuItem correctTest = new MenuItem("Rätta Test");
        MenuItem changeTest = new MenuItem("Ändra Test");
        MenuItem doTest = new MenuItem("Gör Test");
        MenuItem reuseTest = new MenuItem("Återanvända Test");
        MenuItem doneTest = new MenuItem("Statistik");

        //Creates a MenuItem for akrivMenu
        MenuItem registerMenu = new MenuItem("Registrera Användare");


        //Creates a menu for settings
        Menu settingsMenu = new Menu("Inställningar");

        //Creates a MenuItem for the settingsMenu
        MenuItem editUser = new MenuItem("Redigera användare");


        //Creates a Menu thats used to logout
        Menu menu4 = new Menu("Logga ut");


        //Adds childrens to all the menues
        arkivMenu.getItems().addAll(testMenu, registerMenu);

        testMenu.getItems().addAll(createTest, shareTest, correctTest, changeTest, doTest, reuseTest, doneTest);

        settingsMenu.getItems().addAll(editUser);

        //Adds all menu to the menubar
        MenuBar menuAdmin = new MenuBar(home, arkivMenu, settingsMenu);

        //Adds all menu to the menubar
        MenuBar menuAdmin2 = new MenuBar(menu4);
        menuAdmin.setPrefWidth(1510); //Double.MAX_VALUE

        HBox hb = new HBox();
        hb.getChildren().addAll(menuAdmin, menuAdmin2);
        pane.getChildren().addAll(hb);



        MenuBarHelper.onAction(home);
        MenuBarHelper.onAction(menu4);

        //ActionEvent for loginbutton.
        menu4.setOnAction(e -> {
            LoginPage lp = new LoginPage(window);
        });

        //ActionEvent for register user.
        registerMenu.setOnAction(e->{
            Register rt = new Register(window);
        });

        //ActionEvent for home button.
        home.setOnAction(e->{
            AdminFirstpage afp = new AdminFirstpage(window);
        });

        //ActionEvent for createtest button.
        createTest.setOnAction(e->{
            FxView fv = new FxView(window);
        });

        //ActionEvent for editUser button.
        editUser.setOnAction(e->{
            Settings st = new Settings(window);
        });

        //ActionEvent for doTest.
        doTest.setOnAction(e->{
            SelectTestView stv = new SelectTestView(window);
        });


        //ActionEvent for statistics.
        doneTest.setOnAction(e->{
            StatisticsView ssv = new StatisticsView(window);
        });

        //ActionEvent for shareTest.
        shareTest.setOnAction(e->{
            ShareTestView stv = new ShareTestView(window);
        });

    }

}
