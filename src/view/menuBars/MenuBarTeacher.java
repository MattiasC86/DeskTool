package view.menuBars;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.StatisticsView;
import view.doTest.DoTestFxView;
import view.doTest.SelectTestView;
import view.homepage.AdminFirstpage;
import view.LoginPage;
import view.Settings;
import view.createTest.FxView;
import view.homepage.TeacherFirstpage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Rasmus on 2017-05-11.
 */
public class MenuBarTeacher {

        public MenuBarTeacher(Pane pane, Stage window) {

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
                MenuItem doneTest = new MenuItem("Statestik");


                //skapar en huvudmeny med namn Inställningar
                Menu menu3 = new Menu("Inställningar");

                //skapar menuItems som ska fylla menyn Inställningar
                MenuItem editUser = new MenuItem("Redigera användare");

                //Skapar en huvudmeny med namn logga ut
                Menu menu4 = new Menu("Logga ut");


                menu2.getItems().addAll(createMenu);

                createMenu.getItems().addAll(createTest, correctTest, changeTest, doTest, reuseTest, doneTest);

                menu3.getItems().addAll(editUser);

                MenuBar menuAdmin = new MenuBar(menu1, menu2, menu3);

                MenuBar menuAdmin2 = new MenuBar(menu4);
                menuAdmin.setPrefWidth(1510); //Double.MAX_VALUE

                HBox hb = new HBox();
                hb.getChildren().addAll(menuAdmin, menuAdmin2);
                pane.getChildren().addAll(hb);

                MenuBarHelper.onAction(menu1);
                MenuBarHelper.onAction(menu4);

                //ActionEvent för loginknappen.
                menu4.setOnAction(e -> {
                        LoginPage lp = new LoginPage(window);
                });

                //ActionEvent för hemknappen.
                menu1.setOnAction(e -> {
                        TeacherFirstpage tfp = new TeacherFirstpage(window);
                });

                //ActionEvent för SkapaTest.
                createTest.setOnAction(e -> {
                        FxView fv = new FxView(window);
                });

                editUser.setOnAction(e -> {
                        Settings st = new Settings(window);
                });

                doTest.setOnAction(e -> {
                        SelectTestView stv = new SelectTestView(window);
                });

                doneTest.setOnAction(e -> {
                        StatisticsView ssv = new StatisticsView(window);
                });

        }

}