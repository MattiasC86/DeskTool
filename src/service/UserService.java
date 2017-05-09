package service;

import entity.Answer;
import entity.Question;
import entity.Test;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class UserService {
    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    private static EntityManager entityManager = emFactory.createEntityManager();

    public static void createUser(User user) {

        entityManager.getTransaction().begin();

        user.setFirstName();
        user.setLastName();
        user.userName();
        user.email();
        user.setPassword();
        user.setRole();

        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void readUser() {
        User user = entityManager.find(User.class, 1);
        return chosenTest;
    }

    public static void updateUser(User user) {}

    public static void deleteUser(User user) {}
}
