package service;


import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


public class UserService {
    

    public static void create(User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static User readById(int userid) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        User user = entityManager.find(User.class, userid);
        return user;
    }

    public static void readByName(String userName){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager();

        /*Query query = entitymanager.createQuery( "Select u " + "from user u " + "where u.firstname ");

        List<User> list=(List<User>)query.getResultList( );

        for( User u:list ){
            System.out.print("user ID :" + u.getUserId( ));
            System.out.println("\t user name :" + u.getFirstName( ));
        }*/

        Query query = entitymanager.
                createQuery("Select UPPER(u.firstName) from User u");
        List<String> list = query.getResultList();

        for(String e:list) {
            System.out.println("Employee NAME :"+e);
        }

    }

    public static void update(User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public static void delete(User user) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
    public static void main(String[] args) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        Integer userid = 1;
        User user = entityManager.find(User.class, userid);
        System.out.println("employee ID = "+user.getUserId( ));
        System.out.println("employee Name = "+user.getFirstName( ));



    }
}
