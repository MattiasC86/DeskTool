package view.menuBars;

import entity.User;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import service.UserService;

/**
 * Created by raker on 2017-05-17.
 */
public class MenuBarHelper {

    public static void onAction(Menu menu){

        final MenuItem menuItem = new MenuItem();

        menu.getItems().add(menuItem);
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
    }

    public static void getMenuBar(Stage window, Pane pane) {
        User user = UserService.read(LoginLogic.getCurrId());

        if (user.getRole().equalsIgnoreCase("Admin")) {
            MenuBarAdmin x = new MenuBarAdmin(pane, window);
        } else if (user.getRole().equalsIgnoreCase("Lärare")) {
            MenuBarTeacher x = new MenuBarTeacher(pane, window);
        } else if (user.getRole().equalsIgnoreCase("Student")) {
            MenuBarStudent x = new MenuBarStudent(pane, window);
        }
    }

}
