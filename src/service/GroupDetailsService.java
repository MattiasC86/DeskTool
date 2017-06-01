package service;

import entity.GroupDetails;
import entity.StudentGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by matti on 2017-06-01.
 */
public class GroupDetailsService {

    public static void create(GroupDetails groupDetails) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(groupDetails);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}
