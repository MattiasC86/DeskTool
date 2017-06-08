/*
* UserLogic contains methods for getting data connected to specific User(s)
*/

package logic;

import entity.Test;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserLogic {

    // Returns all Tests available to User
    public static List<Test> getAvailableTests (User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select t from Test t inner join TestAccess ta on ta.test.testId = t.testId WHERE ta.user.userId = " + user.getUserId());

        List<Test> testList = (List<Test>)query.getResultList();
        List<Test> dateCheckedTestList = new ArrayList<>();

        Date utilDate = Calendar.getInstance().getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        // If current time is between start and end dates of Test, test is added to dateCheckedTestList
        for(Test test : testList) {
            if((test.gettStartTestDate().before(sqlDate) || test.gettStartTestDate().equals(sqlDate))) {
                if(sqlDate.before(test.gettEndTestDate()) || sqlDate.toString().equals(test.gettEndTestDate().toString())) {
                    dateCheckedTestList.add(test);
                }
            }
        }

        entityManager.close();
        emFactory.close();

        List<Test> atList = checkIfAnswered(user);
        List<Test> removeList = new ArrayList<>();

        for(Test test : dateCheckedTestList) {
            for(Test aTest : atList) {
                if(test.getTestId() == aTest.getTestId()) {
                    removeList.add(test);
                }
            }
        }

        for(Test rTest : removeList) {
            dateCheckedTestList.remove(rTest);
        }
        return dateCheckedTestList;
    }

    // Returns all AnsweredTests by User
    public static List<Test> checkIfAnswered (User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select t from Test t inner join AnsweredTest at on at.test.testId = t.testId WHERE at.user.userId = " + user.getUserId());

        List<Test> atList = (List<Test>)query.getResultList();

        entityManager.close();
        emFactory.close();

        return atList;
    }
}
