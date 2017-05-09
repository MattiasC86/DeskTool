package service;

import entity.Answer;
import entity.Question;
import entity.Test;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;


public class QuestionService {

    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    private static EntityManager entityManager = emFactory.createEntityManager();

    public static void create(Question question) {
        entityManager.getTransaction().begin();
        entityManager.persist(question);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(Question questions[]) {
        entityManager.getTransaction().begin();

        for(Question question: questions) {
            entityManager.persist(question);
        }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static Question read(int questionId) {
        Question chosenQuestion = entityManager.find(Question.class, questionId);
        return chosenQuestion;
    }

    

}
