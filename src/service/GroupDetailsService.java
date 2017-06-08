/*
* Service class for GroupDetails entity
*/

package service;

import entity.GroupDetails;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


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

    public static List<GroupDetails> readAll() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        List<GroupDetails> groupDetailsList = entityManager.createQuery("select gd from GroupDetails gd").getResultList();

        entityManager.close();
        emFactory.close();

        return groupDetailsList;
    }

    public static void delete(List<GroupDetails> groupDetailsList) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        for(GroupDetails gDetails : groupDetailsList) {
            GroupDetails gDetail = entityManager.find(GroupDetails.class, gDetails.getGroupDetailsId());
            entityManager.remove(gDetail);
        }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}
