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

            case "firstName":
                Query firstNameQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.firstName" + "= '" + keyword + "'");

                List<User> firstNameList=(List<User>)firstNameQuery.getResultList( );

                for( User u:firstNameList ){
                    userinfo = u.getFirstName();
                }
                break;

            case "lastName":
                Query lastNameQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.lastName" + "= '" + keyword + "'");

                List<User> lastNameList=(List<User>)lastNameQuery.getResultList( );

                for( User u:lastNameList ){
                    userinfo = u.getLastName();
                }
                break;

            case "password":
                Query passwordQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.password" + "= '" + keyword + "'");

                List<User> passwordList=(List<User>)passwordQuery.getResultList( );

                for( User u:passwordList ){
                    userinfo = u.getPassword();
                }
                break;

            case "userName":
                Query userNameQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.userName" + "= '" + keyword + "'");

                List<User> userNameList=(List<User>)userNameQuery.getResultList( );

                for( User u:userNameList ){
                    userinfo = u.getUserName();
                }
                break;

            case "email":
                Query emailQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.email" + "= '" + keyword + "'");

                List<User> emailList=(List<User>)emailQuery.getResultList( );

                for( User u:emailList ){
                    userinfo = u.getEmail();
                }
                break;

            case "role":
                Query roleQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.role" + "= '" + keyword + "'");

                List<User> roleList=(List<User>)roleQuery.getResultList( );

                for( User u:roleList ){
                    userinfo = u.getRole();
                }
                break;
        }
        return userinfo;
    }

    public static String read2(String criteria, String keyword, String youwant){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager();
        String userinfo = "";

        switch(criteria) {

            case "userName":
                Query username2Query = entitymanager.createQuery( "Select u " + "from User u " + "where u.userName" + "= '" + keyword + "'");

                List<User> username2List=(List<User>)username2Query.getResultList( );

                for( User u:username2List ){

                    switch (youwant) {

                        case "userId":
                            userinfo = Integer.toString(u.getUserId());
                            break;

                        case "userName":
                            userinfo = u.getUserName();
                            break;

                        case "password":
                            userinfo = u.getPassword();
                            break;

                        case "role":
                            userinfo = u.getRole();
                            break;

                    }
                }
                break;

            case "password":
                Query password2Query = entitymanager.createQuery( "Select u " + "from User u " + "where u.password" + "= '" + keyword + "'");

                List<User> password2List=(List<User>)password2Query.getResultList( );

                for( User u:password2List ){
                    switch (youwant) {

                        case "userId":
                            userinfo = Integer.toString(u.getUserId());
                            break;

                        case "userName":
                            userinfo = u.getUserName();
                            break;

                        case "password":
                            userinfo = u.getPassword();
                            break;

                        case "role":
                            userinfo = u.getRole();
                            break;

                    }
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

        System.out.println("Username " + read("userName", "Crusse"));
        System.out.println("Username " + read2("userName", "Crusse", "password"));

    }
}
