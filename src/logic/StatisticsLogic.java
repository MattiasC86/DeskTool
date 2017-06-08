/*
* StatisticsLogic contains logic methods used for getting and calculating
* different values used for displaying Test statistics
*/

package logic;

import entity.AnsweredTest;
import entity.TestAccess;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by matti on 2017-05-23.
 */
public class StatisticsLogic {

    // Returns number of User's with access to specific Test
    public static int getNrAccess(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select ta from TestAccess ta where ta.test.testId = " + testId);
        List<TestAccess> taList = (List<TestAccess>)query.getResultList();

        int nrAccess = 0;
        for(TestAccess element : taList) {
            nrAccess++;
        }
        entityManager.close();
        emFactory.close();

        return nrAccess;
    }

    // Returns number of User's who have completed specific Test
    public static int getNrDone(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.test.testId = " + testId);
        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();

        int nrDone = 0;
        for(AnsweredTest element : atList) {
            nrDone++;
        }
        entityManager.close();
        emFactory.close();

        return nrDone;
    }

    // Returns how many of all User's passed the Test
    public static int getNrPassed(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.test.testId = " + testId + " and " +
                "(at.aTGrade = 'G' or at.aTGrade = 'VG')");

        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();
        int nrPassed = 0;
        for(AnsweredTest element : atList) {
            nrPassed++;
        }
        entityManager.close();
        emFactory.close();

        return nrPassed;
    }

    // Returns how many of selected User's passed the Test
    public static int getNrPassed(List<User> users, int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        int nrPassed = 0;
        for(User user : users) {
            Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.test.testId = " + testId + " and " +
                    "at.user.userId = " + user.getUserId() + " and " + "(at.aTGrade = 'G' or at.aTGrade = 'VG')");

            List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();
            for(AnsweredTest element : atList) {
                nrPassed++;
            }
        }
        entityManager.close();
        emFactory.close();

        return nrPassed;
    }

    // Retuns avarage score of selected AnsweredTest
    public static double getAvgScore(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.test.testId = " + testId);
        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();

        int nrAnswered = 0;
        for(AnsweredTest element : atList) {
            nrAnswered++;
        }
        int totalScore = 0;
        for(AnsweredTest element : atList) {
            totalScore += element.getaTPoints();
        }

        double avgScore = totalScore/nrAnswered;


        entityManager.close();
        emFactory.close();

        return avgScore;
    }

    // Returns avg score for specific User's and specific AnsweredTest
    public static double getAvgScore(List<User> users, int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        int nrAnswered = 0;
        int totalScore = 0;

        for(User user : users) {
            Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.test.testId = " + testId +
                " and at.user.userId = " + user.getUserId());

            List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();
            for(AnsweredTest element : atList) {
                nrAnswered++;
                totalScore += element.getaTPoints();
            }
        }

        double avgScore = totalScore/nrAnswered;

        entityManager.close();
        emFactory.close();

        return avgScore;
    }

    // Returns avarage time for selected Test and all User's
    public static int getAvgTime(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.test.testId = " + testId);
        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();

        int nrAnswered = 0;
        for(AnsweredTest element : atList) {
            nrAnswered++;
        }
        int totalTime = 0;
        for(AnsweredTest element : atList) {
            totalTime += element.getaTTimeSec();
        }

        int avgTime = totalTime/nrAnswered;

        entityManager.close();
        emFactory.close();

        return avgTime;
    }

    // Returns avarage time for selected Test and Users
    public static int getAvgTime(List<User> users, int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        int nrAnswered = 0;
        int totalTime = 0;

        for(User user : users) {
            Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.test.testId = " + testId +
                " and at.user.userId = " + user.getUserId());
            List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();

            for(AnsweredTest element : atList) {
                nrAnswered++;
                totalTime += element.getaTTimeSec();
            }
        }

        int avgTime = totalTime/nrAnswered;

        entityManager.close();
        emFactory.close();

        return avgTime;
    }

    // Returns all AnsweredTest's which has been graded
    public static List<AnsweredTest> getCorrected(int userId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select at from AnsweredTest at where at.user.userId = " + userId + " and " +
                "(at.aTGrade = 'IG' or at.aTGrade = 'G' or at.aTGrade = 'VG')");
        List<AnsweredTest> atList = (List<AnsweredTest>)query.getResultList();

        entityManager.close();
        emFactory.close();

        return atList;
    }
}