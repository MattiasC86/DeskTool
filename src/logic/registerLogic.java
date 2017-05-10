package logic;

import entity.Test;
import entity.User;
import service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by matti on 2017-05-09.
 */
public class registerLogic {

    public static void registerUser(String firstName, String lastName, String userName, String email, String password, String role) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = emFactory.createEntityManager();

        ArrayList<String> keywords = new ArrayList<String>();
        keywords.add(userName);
        keywords.add(email);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        List<Predicate> predicates = new LinkedList<>();
        for (String keyword : keywords) {
            predicates.add(builder.like(root.<String>get("Crusse"), "%" + keyword + "%"));
        }

        System.out.println(entityManager.createQuery(
                query.select(root).where(
                        builder.or(
                                predicates.toArray(new Predicate[predicates.size()])
                        )
                ))
                .getResultList());




        //UserService.create(new User(firstName, lastName, userName, email, password, role));

    }


}
