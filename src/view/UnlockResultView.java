// This class is a page where an Admin or Teacher can choose to

package view;

import entity.Test;
import entity.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.LoginLogic;
import service.TestService;
import service.UserService;
import view.menuBars.MenuBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-06-06.
 */
public class UnlockResultView {
    Label lUnlock;
    Label lSelectTest;
    Label lStatus;

    List<Test> testList;
    Pane pane;
    ComboBox testBox;
    Button btnUnlock;


    public UnlockResultView(Stage window) {

        User user = UserService.read(LoginLogic.getCurrId());

        pane = new Pane();


        MenuBarHelper.getMenuBar(window, pane);

        testBox = new ComboBox();
        btnUnlock = new Button("Lås upp resultat");
        testList = new ArrayList<>();

        lUnlock = new Label("Tryck för att öppna samtliga elevers\nåtkomst till resultatsidan för valt prov:");
        lSelectTest = new Label("Välj prov:");
        lStatus = new Label("");
        lStatus.setVisible(false);

        // If user is Admin, all tests are added to list
        // If user is Teacher, only tests created by this teacher are added to list
        switch(UserService.read(LoginLogic.getCurrId()).getRole()) {
            case "Admin":
                testList = TestService.readAll();
                break;
            case "Teacher":
                testList = TestService.readAll(LoginLogic.getCurrId());
                break;
        }

        // Fills testBox with test titles
        for(Test test : testList) {
            testBox.getItems().add(test.gettTitle());
        }

        lSelectTest.relocate(300,200);
        testBox.relocate(300,230);
        lUnlock.relocate(300, 350);
        btnUnlock.relocate(300,400);
        lStatus.relocate(300, 450);

        pane.getChildren().addAll(testBox, btnUnlock, lUnlock, lSelectTest, lStatus);

        Scene scene = new Scene(pane, 1600, 900);
        window.setScene(scene);
        window.show();

        testBox.setOnAction(e -> lStatus.setVisible(false));

        // When btn is clicked, selected tests var tDisplayResult is set to 1
        btnUnlock.setOnAction(e -> {
            int selIndex = testBox.getSelectionModel().getSelectedIndex();
            int returnCode = TestService.update(testList.get(selIndex));
            System.out.println("RETURNCODE: " + returnCode);

            // User gets notified if Test already was displaying results or if it is now unlocked
            switch(returnCode) {
                case 0:
                    lStatus.setText("Samtliga elever har redan tillgång till valt prov.");
                    lStatus.setVisible(true);
                    break;
                case 1:
                    lStatus.setText("Tillgång till resultatsidan för valt prov upplåst.");
                    lStatus.setVisible(true);
                    break;
            }
        });
    }

}
