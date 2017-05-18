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
        User user2 = new User("Olof", "Olofsson", "Olofmeister", "olof@meister.com", "olof", "Student");


        Test test1 = new Test("Djungeltest", 20, 100, 1, user1);
        Test test2 = new Test("Newton Quiz", 15, 5, 1, user1);


        Question question1 = new Question("Hur många bor det i skogen?", 1, "Single", 0, "G", test1);
        Question question2 = new Question("Vad är en björn?", 1, "Multiple", 1, "G", test1);
        Question question3 = new Question("Vad är en Newton?", 1, "Single", 0, "G", test2);
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        Answer answer1 = new Answer("20 personer", 1, 0, question1);
        Answer answer4 = new Answer("10 personer", 1, 0, question1);
        Answer answer5 = new Answer("Ett djur", 1, 0, question2);
        Answer answer6 = new Answer("En stad", 1, 0, question2);
        Answer answer2 = new Answer("En yrkeshögskola", 1, 0, question3);
        Answer answer3 = new Answer("En förskola", 0, 1, question3);
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);


        UserService.create(user1);
        UserService.create(user2);
        create(test1);
        create(test2);
        QuestionService.create(questions);
        AnswerService.create(answers);
        TestAccessService.create(user1, test1);
        TestAccessService.create(user1, test2);
        TestAccessService.create(user2, test2);
    }
}