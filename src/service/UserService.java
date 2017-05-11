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

    public static String read(String criteria, String keyword, String youwant){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager();
        String userinfo = "";

        switch(criteria) {

            case "firstName":
                Query firstNameQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.firstName" + "= '" + keyword + "'");

                List<User> firstNameList=(List<User>)firstNameQuery.getResultList( );

                for( User u:firstNameList ){
                    switch (youwant) {

                        case "userId": userinfo = Integer.toString(u.getUserId());
                            break;

                        case "firstName": userinfo = u.getFirstName();
                            break;

                        case "lastName": userinfo = u.getLastName();
                            break;

                        case "email": userinfo = u.getEmail();
                            break;

                        case "userName": userinfo = u.getUserName();
                            break;

                        case "password": userinfo = u.getPassword();
                            break;

                        case "role": userinfo = u.getRole();
                            break;

                    }
                }
                break;

            case "lastName":
                Query lastNameQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.lastName" + "= '" + keyword + "'");

                List<User> lastNameList=(List<User>)lastNameQuery.getResultList( );

                for( User u:lastNameList ){
                    switch (youwant) {

                        case "userId": userinfo = Integer.toString(u.getUserId());
                            break;

                        case "firstName": userinfo = u.getFirstName();
                            break;

                        case "lastName": userinfo = u.getLastName();
                            break;

                        case "email": userinfo = u.getEmail();
                            break;

                        case "userName": userinfo = u.getUserName();
                            break;

                        case "password": userinfo = u.getPassword();
                            break;

                        case "role": userinfo = u.getRole();
                            break;

                    }
                }
                break;

            case "userName":
                Query usernameQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.userName" + "= '" + keyword + "'");

                List<User> usernameList=(List<User>)usernameQuery.getResultList( );

                for( User u:usernameList ){

                    switch (youwant) {

                        case "userId":
                            userinfo = Integer.toString(u.getUserId());
                            break;

                        case "firstName":
                            userinfo = u.getFirstName();
                            break;

                        case "lastName":
                            userinfo = u.getLastName();
                            break;

                        case "email":
                            userinfo = u.getEmail();
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
                Query passwordQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.password" + "= '" + keyword + "'");

                List<User> passwordList=(List<User>)passwordQuery.getResultList( );

                for( User u:passwordList ){
                    switch (youwant) {

                        case "userId":
                            userinfo = Integer.toString(u.getUserId());
                            break;

                        case "firstName":
                            userinfo = u.getFirstName();
                            break;

                        case "lastName":
                            userinfo = u.getLastName();
                            break;

                        case "email":
                            userinfo = u.getEmail();
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

            case "email":
                Query emailQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.email" + "= '" + keyword + "'");

                List<User> emailList=(List<User>)emailQuery.getResultList( );

                for( User u:emailList ){
                    switch (youwant) {

                        case "userId":
                            userinfo = Integer.toString(u.getUserId());
                            break;

                        case "firstName":
                            userinfo = u.getFirstName();
                            break;

                        case "lastName":
                            userinfo = u.getLastName();
                            break;

                        case "email":
                            userinfo = u.getEmail();
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

            case "role":
                Query roleQuery = entitymanager.createQuery( "Select u " + "from User u " + "where u.role" + "= '" + keyword + "'");

                List<User> roleList=(List<User>)roleQuery.getResultList( );

                for( User u:roleList ){
                    switch (youwant) {

                        case "userId":
                            userinfo = Integer.toString(u.getUserId());
                            break;

                        case "firstName":
                            userinfo = u.getFirstName();
                            break;

                        case "lastName":
                            userinfo = u.getLastName();
                            break;

                        case "email":
                            userinfo = u.getEmail();
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

    public static void update(int userId, String dataToChange, String newData) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        User user = entityManager.find(User.class, userId);

        entityManager.getTransaction().begin();

        switch(dataToChange) {
            case "password":
                user.setPassword(newData);
                break;
            case "email":
                user.setEmail(newData);
                break;
        }

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

        System.out.println("Username " + read("userName", "Crusse", "role"));

    }
}
