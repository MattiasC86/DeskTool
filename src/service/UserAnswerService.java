package service;

import entity.Answer;
import entity.AnsweredTest;
import entity.User;
import entity.UserAnswer;
import logic.TestLogic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-29.
 */
public class UserAnswerService {



    // Returns a List of UserAnswers based on questionId
    public static List<UserAnswer> read(int questionId, AnsweredTest aTest) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select ua from UserAnswer ua where ua.question.questionId = " + questionId +
         " and ua.answeredTest.answeredTestId = " + aTest.getAnsweredTestId());
        List<UserAnswer> uAnswerList = (List<UserAnswer>)query.getResultList();

        entityManager.close();
        emFactory.close();

        return uAnswerList;
    }

    public static void create(UserAnswer uAnswer) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(uAnswer);

        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(List<UserAnswer> uAnswers) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();

        for(UserAnswer uAnswer: uAnswers) {
            entityManager.persist(uAnswer);
        }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();

        TestLogic.selfCorrectTest(uAnswers.get(0).getAnsweredTest());
    }
}
