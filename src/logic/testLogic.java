package logic;

import entity.Question;
import entity.Test;
import entity.User;
import service.QuestionService;
import service.TestService;
import service.UserService;
import view.createTest.PreQuestion;

import java.util.ArrayList;

/**
 * Created by matti on 2017-05-10.
 */
public class TestLogic {

    public static void saveTest(ArrayList<PreQuestion> list, String title, int selfCorrecting, int timeMin, User user) {

        int nrOfQuestions = list.size();

        System.out.println("Title: " + title);
        System.out.println("timeMin: " + timeMin);
        System.out.println("nrOfQuestions/points: " + nrOfQuestions);
        System.out.println("selfCorrecting: " + selfCorrecting);
        System.out.println("User: " + user.getUserName());

        Test test = new Test(title, timeMin, nrOfQuestions, selfCorrecting, user);


        TestService.create(test);

        ArrayList<entity.Question> questions = new ArrayList<>();


        int i = 0;
        for(PreQuestion element: list) {
            System.out.println(i);
            String questionType = "";
            switch(element.getType()) {
                case 0: questionType = "single";
                break;
                case 1: questionType = "multiple";
                    break;
                case 2: questionType = "rank";
                    break;
            }
            Question question = new Question(element.QuestionField.getText(), 1, questionType, i, "G", test);
            questions.add(question);
            i++;
        }
        QuestionService.create(questions);
        //TestService.create(test, questions, answers);
    }

}
