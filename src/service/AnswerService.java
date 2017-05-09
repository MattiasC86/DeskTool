package service;

import entity.Answer;
import entity.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;


public class AnswerService {

    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    private static EntityManager entityManager = emFactory.createEntityManager();

    public static void create(Answer answer) {
        entityManager.getTransaction().begin();
        entityManager.persist(answer);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(ArrayList<Answer> answers) {
        entityManager.getTransaction().begin();

        for(Answer answer: answers) {
            entityManager.persist(answer);
        }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static Answer read(int answerId) {
        Answer chosenAnswer = entityManager.find(Answer.class, answerId);
        return chosenAnswer;
    }

    public static void update(Answer answer) {
        entityManager.getTransaction().begin();
        entityManager.persist(answer);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void delete(Answer answer) {
        entityManager.getTransaction().begin();
        entityManager.remove(answer);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}
