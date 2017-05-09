package service;

import entity.Answer;
import entity.Question;
import entity.Test;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;


public class TestService {
    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    private static EntityManager entityManager = emFactory.createEntityManager();

    public static void create(Test test, ArrayList<Question> questions, ArrayList<Answer> answers, User user) {

        entityManager.getTransaction().begin();

        entityManager.persist(user);
        entityManager.persist(test);

        for(Question element : questions) {
            entityManager.persist(element);
        }

        for(Answer element : answers) {
            entityManager.persist(element);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();

        /*createQuestion(question);
        createAnswer(answer);*/
    }

    public static Test read(int testId) {
        Test chosenTest = entityManager.find(Test.class, testId);
        return chosenTest;
    }

    public static void update(Test test) {
        entityManager.getTransaction().begin();
        entityManager.persist(test);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void delete(Test test) {
        entityManager.getTransaction().begin();
        entityManager.remove(test);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void main(String[] args) {

        User user1 = new User("Mattias", "Crusell", "goteborg", "Admin");

        Test test1 = new Test("Djungeltest", 20, 100, 1, user1);

        Question question1 = new Question("Hur många bor det i skogen?", 2, "Single", 1, "G", test1);
        Question question2 = new Question("Vad är en björn?", 2, "Multiple", 2, "G", test1);
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(question1);
        questions.add(question2);

        Answer answer1 = new Answer("20 personer", 5, 1, question1);
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(answer1);

        create(test1, questions, answers, user1);

        /*EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        User user1 = new User("Mattias", "Crusell", "goteborg", "Admin");
        entitymanager.persist(user1);

        Test test1 = new Test("Djungeltest", 20, 100, 1, user1);
        Test test2 = new Test("Djungeltest2", 20, 100, 1, user1);
        entitymanager.persist(test1);
        entitymanager.persist(test2);

        Question question1 = new Question("Hur många bor det i skogen?", 2, "Single", 1, "G", test1);
        Question question2 = new Question("Vad är en björn?", 2, "Multiple", 2, "G", test1);
        entitymanager.persist(question1);
        entitymanager.persist(question2);
        question2.setTest(test2);
        entitymanager.persist(question2);

        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
        emfactory.close( );*/
    }
}