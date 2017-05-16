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
        return chosenTest;
    }

    public static void update(Test test) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(test);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
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

        Test test1 = new Test("Djungeltest", 20, 100, 1, user1);

        Question question1 = new Question("Hur många bor det i skogen?", 2, "Single", 1, "G", test1);
        Question question2 = new Question("Vad är en björn?", 2, "Multiple", 2, "G", test1);
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(question1);
        questions.add(question2);

        Answer answer1 = new Answer("20 personer", 5, 1, question1);
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(answer1);

        UserService.create(user1);
        create(test1, questions, answers);
    }
}