/*
* Service class for TestAccess entity
*/

package service;

import entity.AnsweredTest;
import entity.Test;
import entity.TestAccess;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matti on 2017-05-17.
 */
public class TestAccessService {

    public static void create(TestAccess testAccess) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(testAccess);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(User user, Test test) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(new TestAccess(user, test));
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(List<User> userList, Test test) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select ta from TestAccess ta where ta.test.testId = " + test.getTestId());
        List<TestAccess> taList = (List<TestAccess>)query.getResultList();

        entityManager.getTransaction().begin();

        int i = 0;
        for(User user : userList) {
            i = 0;
            for(TestAccess tA : taList) {
                if(user.getUserId() == tA.getUser().getUserId()) {
                    break;
                }
                if(i == taList.size() - 1) {
                    entityManager.persist(new TestAccess(user, test));
                }
                i++;
            }
        }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    // Returns nr of TestAccess for specific users and specific test
    public static int read(List<User> users, int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        int nrAccess = 0;

        // This for loop adds 1 to nrAccess each time match is found
        for(User user : users) {
            Query query = entityManager.createQuery( "Select ta from TestAccess ta where ta.user.userId = " + user.getUserId() +
                    " and ta.test.testId = " + testId);
            List<TestAccess> taList = (List<TestAccess>)query.getResultList();
            for(TestAccess ta : taList) {
                nrAccess++;
            }
        }
        entityManager.close();
        emFactory.close();
        return nrAccess;
    }

    public static void update() {

    }

    public static void delete() {

    }
}
