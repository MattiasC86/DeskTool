package view.menuBars;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.*;
import view.statistics.StatisticsView;
import view.doTest.SelectTestView;
import view.createTest.FxView;
import view.homepage.TeacherFirstpage;

/**
 * Created by Rasmus on 2017-05-11.
 */
public class MenuBarTeacher {

        //Menubar for teacher user
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

                //Menu "Arkiv"
                Menu menu2 = new Menu("Arkiv");

                //Menu "Test"
                Menu createMenu = new Menu("Test");

                //Menuitems to fill the menu
                MenuItem createTest = new MenuItem("Skapa Test");
                MenuItem shareTest = new MenuItem("Dela Test");
                MenuItem shareResultTest = new MenuItem("Dela resultat");
                MenuItem makeGroups = new MenuItem("Skapa grupper");
                MenuItem doneTest = new MenuItem("Statistik");


                //Menu settings
                Menu menu3 = new Menu("Inställningar");

                //Menu change users
                MenuItem editUser = new MenuItem("Redigera användare");

                //Menu loggout
                Menu menu4 = new Menu("Logga ut");


                menu2.getItems().addAll(createMenu,makeGroups);

                createMenu.getItems().addAll(createTest, shareTest, shareResultTest, doneTest);

                menu3.getItems().addAll(editUser);

                MenuBar menuAdmin = new MenuBar(menu1, menu2, menu3);

                MenuBar menuAdmin2 = new MenuBar(menu4);
                menuAdmin.setPrefWidth(1510); //Double.MAX_VALUE

                HBox hb = new HBox();
                hb.getChildren().addAll(menuAdmin, menuAdmin2);
                pane.getChildren().addAll(hb);

                MenuBarHelper.onAction(menu1);
                MenuBarHelper.onAction(menu4);

                //ActionEvent for loggin
                menu4.setOnAction(e -> {
                        LoginPage lp = new LoginPage(window);
                });

                //ActionEvent for home.
                menu1.setOnAction(e -> {
                        TeacherFirstpage tfp = new TeacherFirstpage(window);
                });

                //ActionEvent for createTEst.
                createTest.setOnAction(e -> {
                        FxView fv = new FxView(window);
                });

                //ActionEvent for shareTest.
                shareTest.setOnAction(e->{
                        ShareTestView stv = new ShareTestView(window);
                });

                editUser.setOnAction(e -> {
                        Settings st = new Settings(window);
                });

                doneTest.setOnAction(e -> {
                        StatisticsView ssv = new StatisticsView(window);
                });

                shareTest.setOnAction(e->{
                        ShareTestView stv = new ShareTestView(window);
                });

                shareResultTest.setOnAction(e->{
                        UnlockResultView urv = new UnlockResultView(window);
                });

                makeGroups.setOnAction(e-> {
                        CreateGroupView cgv = new CreateGroupView(window);
                });

        }

}