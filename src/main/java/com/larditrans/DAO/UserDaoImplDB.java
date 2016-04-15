package com.larditrans.DAO;

import com.larditrans.DAO.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
@Component
public class UserDaoImplDB implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public List<User> getAll() {
        final Query query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return (List<User>) query.getResultList();
    }
}
