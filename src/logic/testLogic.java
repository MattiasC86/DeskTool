package logic;

import entity.Answer;
import entity.Question;
import entity.Test;
import entity.User;
import service.AnswerService;
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

        // Saves Test entity to database
        Test test = new Test(title, timeMin, nrOfQuestions, selfCorrecting, user);
        TestService.create(test);

        // ArrayLists for Question and Answer entities
        ArrayList<entity.Question> questions = new ArrayList<>();
        ArrayList<entity.Answer> answers = new ArrayList<>();

        // Creates Question entities and puts in ArrayList questions
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

            // Creates Answer entities and puts in ArrayList answers
            for(int d = 0; d < element.CBox.getValue(); d++){
                int correct;
                if((element.getType() == 0) || (element.getType() == 1) && element.answerBox[d].isSelected()){
                    correct = 1;
                }
                else{
                    correct = 0;
                }
                Answer answer = new Answer(element.answerField[d].getText(), correct, d, question);
                answers.add(answer);
            }
        }

        // Saves all Question and Answer objects to database
        QuestionService.create(questions);
        AnswerService.create(answers);
    }

}
