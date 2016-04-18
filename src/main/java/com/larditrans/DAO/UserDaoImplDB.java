package com.larditrans.dao;

import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * Created by sergey on 14.04.2016.
 */
@Component
@Transactional
@Repository
public class UserDaoImplDB implements UserDao {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User add(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    private User getById(Long id) {
        final Query query = entityManager.createQuery("SELECT u FROM User u where u.id=:id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public User getByLogin(String login) {
        final Query query = entityManager.createQuery("SELECT u FROM User u where u.login=:login", User.class);
        query.setParameter("login", login);
        User result = null;
        try
        {
            result = (User) query.getSingleResult();
        }
        catch (NoResultException ex)  {}
        return result;
    }
}
