package service;

import entity.GroupDetails;
import entity.StudentGroup;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by matti on 2017-05-24.
 */
public class StudentGroupService {

    public static void create(StudentGroup studentGroup) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(studentGroup);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void create(String groupName, List<User> users) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
            StudentGroup studentGroup = new StudentGroup(groupName);
            entityManager.persist(studentGroup);
            for(User user : users) {
                entityManager.persist(new GroupDetails(studentGroup, user));
            }
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    // Returns all StudentGroups
    public static List<StudentGroup> readAll() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        List<StudentGroup> sgList = entityManager.createQuery("select sg from StudentGroup sg").getResultList();

        entityManager.close();
        emFactory.close();

        return sgList;
    }

    // Returns all members in selected StudentGroup
    public static List<User> readByGroup(int studentGroupId) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        List<User> userList = entityManager.createQuery("select gd.user from GroupDetails gd where gd.StudentGroup.StudentGroupId = " + studentGroupId)
                .getResultList();

        entityManager.close();
        emFactory.close();

        return userList;
    }

    // Deletes selected StudentGroup
    public static void delete(StudentGroup studentGroup) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        List<GroupDetails> groupDetailsList = entityManager.createQuery("select gd from GroupDetails gd where " +
                "gd.studentGroup.StudentGroupId = " + studentGroup.getStudentGroupId()).getResultList();
        GroupDetailsService.delete(groupDetailsList);

        StudentGroup sGroup = entityManager.find(StudentGroup.class, studentGroup.getStudentGroupId());

        entityManager.getTransaction().begin();
        entityManager.remove(sGroup);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}
