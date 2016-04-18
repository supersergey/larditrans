package com.larditrans.dao;


import com.larditrans.model.User;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Created by sergey on 14.04.2016.
 */
@Repository
public class UserDaoImplDb implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User add(User user) {
        em.persist(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(User user) {
        em.remove(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        return user;
    }

    private User getById(Long id) {
        final Query query = em.createQuery("SELECT u FROM User u where u.id=:id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public User getByLogin(String login) {

        final Query query = em.createQuery("SELECT u FROM User u where u.login=:login", User.class);
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
