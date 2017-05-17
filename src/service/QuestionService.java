package service;

import entity.Answer;
import entity.Question;
import entity.Test;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class QuestionService {

    public static void create(Question question) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(question);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(ArrayList<Question> questions) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();

        for(Question question: questions) {
            entityManager.persist(question);
        }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    // Returns List of Questions based on testId
    public static List<Question> read(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select q from Question q where q.test.testId = " + testId);
        List<Question> questionList = (List<Question>)query.getResultList();

        return questionList;
    }

    public static void update(Question question) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(question);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void delete(Question question) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(question);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

}
