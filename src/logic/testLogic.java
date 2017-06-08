/*
* TestLogic contains methods used in conjunction with Test creation annd answered tests self correcting
*/

package logic;

import entity.*;
import service.*;
import view.createTest.PreQuestion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
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
    public static void selfCorrectTest(AnsweredTest answeredTest) {
        if(answeredTest.getTest().gettSelfCorrecting() == 1) {
            List<Question> questions = QuestionService.read(answeredTest.getTest().getTestId());
            int totalScore = 0;

            for(Question question : questions) {
                List<Answer> answers = AnswerService.read(question.getQuestionId());
                List<UserAnswer> uAnswers = UserAnswerService.read(question.getQuestionId(), answeredTest);

                switch(question.getqType()) {
                    case "Single":
                        for(int i = 0; i < answers.size(); i++) {
                            if((answers.get(i).getaPoints() == 1) && (uAnswers.get(i).getUACheckedAnswer() == 1)) {
                                totalScore++;
                                System.out.println("Ett single-poäng");
                            }
                        }
                        break;

                    case "Multiple":
                        for(int i = 0; i < answers.size(); i++) {
                            if(answers.get(i).getaPoints() != uAnswers.get(i).getUACheckedAnswer()){
                                break;
                            }
                            if(i == answers.size() - 1) {
                                System.out.println("Ett multipoäng");
                                totalScore++;
                            }
                        }
                        break;

                    case "Ranked":
                        for(int i = 0; i < answers.size(); i++) {
                            if(!answers.get(i).getaText().equals(uAnswers.get(i).getUAText())) {
                                break;
                            }
                            if(i == answers.size() - 1) {
                                System.out.println("Ett rankpoäng");
                                totalScore++;
                            }
                        }
                        break;
                }
            }
            EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
            EntityManager entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();

            AnsweredTest aTest = entityManager.find(AnsweredTest.class, answeredTest.getAnsweredTestId());
            aTest.setaTPoints(totalScore);
            double d = totalScore * 1.0 / aTest.getTest().gettMaxPoints();
            if(d >= 0.6) {
                aTest.setaTGrade("G");
            } else {
                aTest.setaTGrade("IG");
            }

            entityManager.getTransaction().commit();
            entityManager.close();
            emFactory.close();
        }
    }


    public static void saveTest(ArrayList<PreQuestion> list, String title, int selfCorrecting, int timeMin, int showResult, Date startDate, Date endDate, User user) {

        int nrOfQuestions = list.size();

        // Saves Test entity to database
        Test test = new Test(title, timeMin, nrOfQuestions, selfCorrecting, showResult, startDate, endDate, user);
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
                case 0: questionType = "Single";
                    break;
                case 1: questionType = "Multiple";
                    break;
                case 2: questionType = "Ranked";
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
