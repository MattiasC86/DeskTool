package logic;

import entity.*;
import service.*;
import view.createTest.PreQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-10.
 */
public class TestLogic {

    public static void saveAnsweredTest() {
        // Ta in test, frågor, svar, tidsåtgång, user
    }

    // Auto corrects an answered test and returns the score.
    // OBS!! Lägg till så att den sparar score specifikt per fråga i AnsweredQuestion, samt att db ska uppdateras med allt
    public static int selfCorrectTest(AnsweredTest answeredTest) {
        List<Question> questions = QuestionService.read(answeredTest.getTest().getTestId());
        int totalScore = 0;

        for(Question question : questions) {
            List<Answer> answers = AnswerService.read(question.getQuestionId());
            List<UserAnswer> uAnswers = UserAnswerService.read(question.getQuestionId());

            switch(question.getqType()) {
                case "Single":
                    for(int i = 0; i < answers.size(); i++) {
                        if((answers.get(i).getaPoints() == 1) && (uAnswers.get(i).getUACheckedAnswer() == 1)) {
                            totalScore++;
                        }
                    }
                    break;

                case "Multiple":
                    for(int i = 0; i < answers.size(); i++) {
                        if(answers.get(i).getaPoints() != uAnswers.get(i).getUACheckedAnswer()){
                            break;
                        }
                    }
                    totalScore++;
                    break;

                case "Ranked":
                    for(int i = 0; i < answers.size(); i++) {
                        if(answers.get(i).getaOrder() != uAnswers.get(i).getUAOrder()) {
                            break;
                        }
                    }
                    totalScore++;
                    break;
            }
        }
        return totalScore;
    }


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
