package view.menuBars;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.statistics.ResultsView;
import view.doTest.SelectTestView;
import view.LoginPage;
import view.Settings;
import view.homepage.StudentFirstpage;

/**
 * Created by Rasmus on 2017-05-11.
 */
public class MenuBarStudent {

    //Menubar for student user
    public MenuBarStudent(Pane pane, Stage window) {

        /*FileInputStream homeIcon = null;
        try {
            homeIcon = new FileInputStream("src/pic/homeIcon.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(homeIcon);
        ImageView homeIV = new ImageView(image);
        homeIV.setFitHeight(15);
        homeIV.setFitWidth(15);
        homeIV.setPreserveRatio(true);*/


        Menu menu1 = new Menu("Hem");

        //Menu "Arkiv"
        Menu menu2 = new Menu("Arkiv");

        //Menu "Test"
        Menu testMenu = new Menu("Test");

        //Menu "items for test"
        MenuItem doTest = new MenuItem("Gör Test");
        MenuItem doneTest = new MenuItem("Rättade Test");

        //Menu for "settings"
        Menu menu3 = new Menu("Inställningar");

        //Menuitem for "settings"
        MenuItem editUser = new MenuItem("Redigera användare");

        //Logg out
        Menu menu4 = new Menu("Logga ut");


        menu2.getItems().addAll(testMenu);

        testMenu.getItems().addAll(doTest, doneTest);

        menu3.getItems().addAll(editUser);

        MenuBar menuAdmin = new MenuBar(menu1, menu2, menu3);

        MenuBar menuAdmin2 = new MenuBar(menu4);
        menuAdmin.setPrefWidth(1510); //Double.MAX_VALUE

        HBox hb = new HBox();
        hb.getChildren().addAll(menuAdmin, menuAdmin2);
        pane.getChildren().addAll(hb);

        MenuBarHelper.onAction(menu1);
        MenuBarHelper.onAction(menu4);

        //ActionEvent for loggin page.
        menu4.setOnAction(e -> {
            LoginPage lp = new LoginPage(window);
        });


        //ActionEvent for homepage.
        menu1.setOnAction(e->{
            StudentFirstpage sfp = new StudentFirstpage(window);
        });


        editUser.setOnAction(e->{
            Settings st = new Settings(window);
        });

        doTest.setOnAction(e->{
            SelectTestView stv = new SelectTestView(window);
        });

        doneTest.setOnAction(e->{
            ResultsView rv = new ResultsView(window);
        });
    }

}

