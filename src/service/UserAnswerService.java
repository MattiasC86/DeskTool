package service;

import entity.Answer;
import entity.UserAnswer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by matti on 2017-05-29.
 */
public class UserAnswerService {

    // Returns a List of UserAnswers based on questionId
    public static List<UserAnswer> read(int questionId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select ua from UserAnswer ua where ua.question.questionId = " + questionId);
        List<UserAnswer> uAnswerList = (List<UserAnswer>)query.getResultList();

        entityManager.close();
        emFactory.close();

        return uAnswerList;
    }
}
