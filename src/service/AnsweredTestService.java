/*
* Service class for AnsweredTest entity
*/

package service;

import entity.*;
import logic.TestLogic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-18.
 */
public class AnsweredTestService {

    public static void create(AnsweredTest answeredTest) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(answeredTest);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(AnsweredTest answeredTest, List<UserAnswer> uAList) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(answeredTest);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();

        UserAnswerService.create(uAList);
        TestLogic.selfCorrectTest(answeredTest);

        System.out.println("(ATESTSERVIC)ANSWEREDTEST SCORE: " + answeredTest.getaTPoints());
    }

    public static AnsweredTest read(int userId, int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.user.userId = " + userId + " and at.test.testId = " + testId);
        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();
        AnsweredTest at = null;
        for(AnsweredTest element : atList) {
            at = element;
        }

        return at;
    }

    public static List<AnsweredTest> read(int userId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.user.userId = " + userId);
        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();

        return atList;
    }

    // Returns list of Tests which User has answered and which has tDisplayResults 1
    public static List<AnsweredTest> readIfResultShared(int userId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.user.userId = " + userId + " and " +
                "at.test.tDisplayResult = 1");

        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();

        return atList;
    }

    // Returns all AnsweredTests for specific users and specific test
    public static List<AnsweredTest> read(List<User> users, int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        List<AnsweredTest> atList = new ArrayList<>();

        // This for loop adds all AnsweredTests for specific test and users in atList
        for(User user : users) {
            Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.user.userId = " + user.getUserId() +
                " and at.test.testId = " + testId);
            List<AnsweredTest> uAtList = (List<AnsweredTest>)query.getResultList();
            for(AnsweredTest at : uAtList) {
                atList.add(at);
            }
        }
        entityManager.close();
        emFactory.close();
        return atList;
    }

    public static void update(AnsweredTest aTest, int score) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        aTest.setaTPoints(score);
        System.out.println("SCORE: " + aTest.getaTPoints());
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}
