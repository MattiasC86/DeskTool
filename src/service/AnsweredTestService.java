package service;

import entity.Answer;
import entity.AnsweredTest;
import entity.Question;
import entity.User;

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
}
