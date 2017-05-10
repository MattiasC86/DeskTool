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

    public static String read(String criteria, String keyword){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager();
        String userinfo = "";

        switch(criteria) {
            case "userName":
                Query userNameQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.userName" + "= '" + keyword + "'");

                List<User> userNameList=(List<User>)userNameQuery.getResultList( );

                for( User u:userNameList ){
                    System.out.println("Email: " + u.getEmail());
                    userinfo = u.getUserName();
                }
                break;
            case "email":
                Query emailQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.email" + "= '" + keyword + "'");

                List<User> passwordList=(List<User>)emailQuery.getResultList( );

                for( User u:passwordList ){
                    userinfo = u.getUserName();
                }
                break;
        }
        return userinfo;
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

        /*Integer userid = 1;
        User user = entityManager.find(User.class, userid);
        System.out.println("employee ID = "+user.getUserId( ));
        System.out.println("employee Name = "+user.getFirstName( ));*/


        System.out.println("Username " + read("email", "crusell@mail.com"));
        //readByName("Mattias");
    }
}
