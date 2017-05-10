package logic;

import entity.Test;
import entity.User;
import javafx.scene.control.ListView;
import service.TestService;

/**
 * Created by matti on 2017-05-10.
 */
public class testLogic {

    public static void saveTest(ListView list, String title, int selfCorrecting, int timeMin, User user) {
        int nrOfQuestions = list.getItems().size();

        Test test = new Test(title, timeMin, nrOfQuestions, selfCorrecting, user);

        TestService.create(test);
    }

}
