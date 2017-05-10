package view;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Rasmus on 2017-05-03.
 */
public class MenuBarAdmin {

    StartView sv;

    public MenuBarAdmin(Pane pane, Stage window){
        sv = new StartView();

        Menu menu1 = new Menu("Hem");

        MenuItem home = new MenuItem("Hem");

        menu1.getItems().addAll(home);

        //skapar en huvudmeny med namn arkiv
        Menu menu2 = new Menu("Arkiv");

        //skapar en submeny med namn test i menyn arkiv
        Menu createMenu = new Menu("Test");

        //skapar menuItems att fylla submenyn Test med
        MenuItem createTest = new MenuItem("Skapa Test");
        MenuItem correctTest = new MenuItem("Rätta Test");
        MenuItem changeTest = new MenuItem("Ändra Test");
        MenuItem doTest = new MenuItem("Gör Test");
        MenuItem reuseTest = new MenuItem("Återanvända Test");


        //skapar en submeny med namn registrera till menyn arkiv
        MenuItem registerMenu = new MenuItem("Registrera Användare");
        /*MenuItem registerStudent = new MenuItem("Registrera Student");
        MenuItem registerTeacher = new MenuItem("Registrera Lärare");*/

        //skapar en huvudmeny med namn Inställningar
        Menu menu3 = new Menu("Inställningar");

        //skapar menuItems som ska fylla menyn Inställningar
        MenuItem changePassword = new MenuItem("Byt Lösenord");
        MenuItem changeEmail = new MenuItem("Byt Email");

        //Skapar en huvudmeny med namn logga ut
        Menu menu4 = new Menu("Logga ut");

        MenuItem logoutItem = new MenuItem("Logga ut");
        menu4.getItems().addAll(logoutItem);

        menu2.getItems().addAll(createMenu, registerMenu);

        createMenu.getItems().addAll(createTest, correctTest, changeTest, doTest, reuseTest);
        //registerMenu.getItems().addAll(registerStudent, registerTeacher);

        menu3.getItems().addAll(changePassword, changeEmail);

        MenuBar menuAdmin = new MenuBar(menu1, menu2, menu3, menu4);

        pane.getChildren().addAll(menuAdmin);

        //ActionEvent för loginknappen.
        logoutItem.setOnAction(e -> {
            sv.logout(window);
        });

        //ActionEvent för Registrera lärare.
        registerMenu.setOnAction(e->{
            sv.registerATeacher(window);
        });

        //ActionEvent för hemknappen.
        home.setOnAction(e->{
            sv.goToHomepage(window);
        });

        createTest.setOnAction(e->{
            sv.goToCreateTest(window);
        });

    }

}
