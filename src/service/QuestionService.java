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

    public static List<Question> read(int testId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        String userinfo = "";

        Query query = entityManager.createQuery( "Select q from Question q where q.test.testId = " + testId);
        System.out.println("före array");
        List<Question> questionList = (List<Question>)query.getResultList();
        System.out.println("efter array");
        for( Question q : questionList){
            System.out.println(q);
        }

        return questionList;

        /*Question chosenQuestion = entityManager.find(Question.class, questionId);
        return chosenQuestion;*/
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
