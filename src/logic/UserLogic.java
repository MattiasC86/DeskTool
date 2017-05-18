package logic;

import entity.Test;
import entity.User;
import service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserLogic {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String role;

    public void UserLogic(String firstName, String lastName, String userName, String password, String email, String role)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public static List<Test> getAvailableTests (User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select t from Test t inner join TestAccess ta on ta.test.testId = t.testId WHERE ta.user.userId = " + user.getUserId());

        List<Test> testList = (List<Test>)query.getResultList();

        entityManager.close();
        emFactory.close();

        List<Test> atList = checkIfAnswered(user);

        System.out.println("KOLLA HÄR!!!! TESTLIST");
        for(Test t : testList) {
            System.out.println(t.gettTitle());
        }

        System.out.println("KOLLA HÄR!!!! ATTESTLIST");
        for(Test at : atList) {
            System.out.println(at.gettTitle());
        }

        List<Test> removeList = new ArrayList<>();

        for(Test test : testList) {
            for(Test aTest : atList) {
                if(test.getTestId() == aTest.getTestId()) {
                    System.out.println("Hittade matchning!!! " + test.gettTitle());
                    removeList.add(test);
                }
            }
        }

        for(Test rTest : removeList) {
            testList.remove(rTest);
        }

        return testList;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {return userName; }

    public void setUserName(String userName) {this.userName = userName; }

    public String getEmail () { return email; }

    public void setEmail (String email) {this.email = email;}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
