/*
* Service class for Test entity
*/

package service;

import entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class TestService {

    public static void create(Test test) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(test);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(Test test, ArrayList<Question> questions, ArrayList<Answer> answers) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(test);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();

        QuestionService.create(questions);
        AnswerService.create(answers);
    }

    public static Test read(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Test chosenTest = entityManager.find(Test.class, testId);

        entityManager.close();
        emFactory.close();

        return chosenTest;
    }

    public static List<Test> readAll() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query testsQuery = entityManager.createNamedQuery( "Test.findAll");
        List<Test> testList = (List<Test>)testsQuery.getResultList();

        return testList;
    }

    public static List<Test> readAll(int userId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query testsQuery = entityManager.createQuery( "Select t from Test t where t.user.userId = " + userId);
        List<Test> testList = (List<Test>)testsQuery.getResultList();

        return testList;
    }

    public static List<Test> readAllByStudent(int userId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query testsQuery = entityManager.createQuery( "Select t from Test t inner join TestAccess ta on t.testId = ta.test.testId where ta.user.userId = " + userId);
        List<Test> testList = (List<Test>)testsQuery.getResultList();

        return testList;
    }

    // Used when unlocking/opening displayResult for test, returns a 0 if already unlocked, otherwise returns a 1
    public static int update(Test test) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Test selTest = entityManager.find(Test.class, test.getTestId());

        if(selTest.gettDisplayResult() == 1) {
            return 0;
        } else {
            entityManager.getTransaction().begin();
            selTest.settDisplayResult(1);
            entityManager.persist(selTest);
            entityManager.getTransaction().commit();

            entityManager.close();
            emFactory.close();

            return 1;
        }




    }

    public static void delete(Test test) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(test);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void main(String[] args) {

        User user1 = new User("Mattias", "Crusell", "Crusse", "crusell@mail.com", "goteborg", "Admin");
        User user2 = new User("Olof", "Olofsson", "Olofmeister", "olof@meister.com", "olof", "Student");

        StudentGroup studentGroup1 = new StudentGroup("Java2");
        GroupDetails groupDetails1 = new GroupDetails(studentGroup1, user1);
        GroupDetails groupDetails2 = new GroupDetails(studentGroup1, user2);

        Test test1 = new Test("Djungeltest", 20, 100, 1, 1, new Date(117,5,1), new Date(117,11,1), user1);
        Test test2 = new Test("Newton Quiz", 15, 5, 1, 1, new Date(117,5,1), new Date(117,11,1), user1);


        Question question1 = new Question("Hur många bor det i skogen?", 1, "Single", 0, "G", test1);
        Question question2 = new Question("Vad är en björn?", 1, "Multiple", 1, "G", test1);
        Question question3 = new Question("Vad är Newton?", 1, "Single", 0, "G", test2);
        Question question4 = new Question("Bästa elev på Newton?", 1, "Ranked", 1, "G", test2);
        Question question5 = new Question("Ranka djurens effektivitet", 1, "Ranked", 2, "G", test1);
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);

        Answer answer1 = new Answer("20 personer", 1, 0, question1);
        Answer answer4 = new Answer("10 personer", 0, 1, question1);
        Answer answer5 = new Answer("Ett djur", 1, 0, question2);
        Answer answer6 = new Answer("En stad", 0, 1, question2);
        Answer answer7 = new Answer("Något som rör sig", 1, 2, question2);
        Answer answer2 = new Answer("En yrkeshögskola", 1, 0, question3);
        Answer answer3 = new Answer("En förskola", 0, 1, question3);
        Answer answer8 = new Answer("Mattias", 0, 0, question4);
        Answer answer9 = new Answer("Övrigt", 0, 2, question4);
        Answer answer10 = new Answer("Okänd elev", 0, 1, question4);
        Answer answer11 = new Answer("Björn", 0, 0, question5);
        Answer answer12 = new Answer("Varg", 0, 1, question5);
        Answer answer13 = new Answer("Gräshoppa", 0, 2, question5);
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);
        answers.add(answer7);
        answers.add(answer8);
        answers.add(answer9);
        answers.add(answer10);
        answers.add(answer11);
        answers.add(answer12);
        answers.add(answer13);


        UserService.create(user1);
        UserService.create(user2);
        StudentGroupService.create(studentGroup1);
        GroupDetailsService.create(groupDetails1);
        GroupDetailsService.create(groupDetails2);
        create(test1);
        create(test2);
        QuestionService.create(questions);
        AnswerService.create(answers);
        TestAccessService.create(user1, test1);
        TestAccessService.create(user1, test2);
        TestAccessService.create(user2, test2);

        AnsweredTestService.create(new AnsweredTest(false, 1, 5, 400, "G", user1, test2));
        //UserAnswerService.create(new UserAnswer());
    }
}