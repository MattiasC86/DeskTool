package service;

import entity.Test;
import entity.TestAccess;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by matti on 2017-05-17.
 */
public class TestAccessService {

    public static void create(TestAccess testAccess) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(testAccess);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(User user, Test test) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(new TestAccess(user, test));
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void read() {

    }

    public static void update() {

    }

    public static void delete() {

    }
}
