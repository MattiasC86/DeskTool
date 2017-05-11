package logic;

import entity.Test;
import entity.User;
import javafx.scene.control.ListView;
import service.TestService;
import service.UserService;



/**
 * Created by matti on 2017-05-10.
 */
public class TestLogic {

    public static void saveTest(ListView list, String title, int selfCorrecting, int timeMin, User user) {

        UserService.create(user);

        int nrOfQuestions = list.getItems().size();

        System.out.println("Title: " + title);
        System.out.println("timeMin: " + timeMin);
        System.out.println("nrOfQuestions/points: " + nrOfQuestions);
        System.out.println("selfCorrecting: " + selfCorrecting);
        System.out.println("User: " + user.getUserName());

        Test test = new Test(title, timeMin, nrOfQuestions, selfCorrecting, user);

        TestService.create(test);
        //TestService.create(test, questions, answers);
    }

}
