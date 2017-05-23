package logic;

import entity.AnsweredTest;
import entity.TestAccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by matti on 2017-05-23.
 */
public class StatisticsLogic {

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
}
