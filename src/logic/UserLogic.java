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

    public static List<Test> getAvailableTests (User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select t from Test t inner join TestAccess ta on ta.test.testId = t.testId WHERE ta.user.userId = " + user.getUserId());

        List<Test> testList = (List<Test>)query.getResultList();
        List<Test> dateCheckedTestList = new ArrayList<>();
        System.out.println("innan datum");
        Date utilDate = Calendar.getInstance().getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        System.out.println("efter datum");
        for(Test test : testList) {
            System.out.println("I TESTLIST FOR LOOP");
            if((test.gettStartTestDate().before(sqlDate) || test.gettStartTestDate().equals(sqlDate))) {
                if(sqlDate.before(test.gettEndTestDate()) || sqlDate.toString().equals(test.gettEndTestDate().toString())) {
                    dateCheckedTestList.add(test);
                    System.out.println(test.gettTitle() + " ADDADES till datecheckedlist!");
                    System.out.println(test.gettStartTestDate() + " vs " + sqlDate);
                }
                else {
                    System.out.println(test.gettTitle() + " PASSERADE INTE");
                    System.out.println(test.gettEndTestDate() + " vs " + sqlDate);
                }
            } else {
                System.out.println("VILLE INTE IN I FÖRSTA IF");
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

    public static List<Test> checkIfAnswered (User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select t from Test t inner join AnsweredTest at on at.test.testId = t.testId WHERE at.user.userId = " + user.getUserId());

        List<Test> atList = (List<Test>)query.getResultList();

        for(Test at : atList) {
            System.out.println(at.gettTitle());
        }

        entityManager.close();
        emFactory.close();

        return atList;
    }
}
