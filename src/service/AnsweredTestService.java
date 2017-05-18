package service;

import entity.Answer;
import entity.AnsweredTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by matti on 2017-05-18.
 */
public class AnsweredTestService {

    public static void create(AnsweredTest answeredTest) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(answeredTest);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}
