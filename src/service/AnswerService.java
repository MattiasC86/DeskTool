/*
* Service class for Answer entity
*/

package service;

import entity.Answer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class AnswerService {

    public static void create(Answer answer) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(answer);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(ArrayList<Answer> answers) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();

        for(Answer answer: answers) {
            entityManager.persist(answer);
        }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    // Returns a List of Answers based on questionId
    public static List<Answer> read(int questionId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Query query = entityManager.createQuery( "Select a from Answer a where a.question.questionId = " + questionId);
        List<Answer> answerList = (List<Answer>)query.getResultList();

        return answerList;
    }

    public static void update(Answer answer) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(answer);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void delete(Answer answer) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(answer);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}
