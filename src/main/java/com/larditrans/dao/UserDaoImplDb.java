package com.larditrans.dao;


import com.larditrans.service.AutoCompleteResult;
import com.larditrans.model.Entry;
import com.larditrans.model.User;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */

@Repository
public class UserDaoImplDb extends AbstractUserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User add(User user) {
        if (null == user)
            throw new IllegalArgumentException("Empty user login is not allowed.");

        if (null == user.getLogin() || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");

        if (null != getByLogin(user.getLogin()))
            throw new IllegalArgumentException("User with the specified login already exists. = " + user.getLogin());

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
        final Query query = em.createQuery("SELECT u FROM User u left join fetch u.entries where u.id=:id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public User getByLogin(String login) {

        final Query query = em.createQuery("SELECT u FROM User u left join fetch u.entries where u.login=:login", User.class);
        query.setParameter("login", login);
        User result = null;
        try {
            result = (User) query.getSingleResult();
        } catch (NoResultException ex) {
        }
        return result;
    }

    @Override
    public int getEntriesCount(String login) {
        User user = getByLogin(login);
        return (null == user.getEntries()) ? 0 : user.getEntries().size();
    }

    @Override
    public List<AutoCompleteResult> autoComplete(String login, String columnName, String term) {
        columnName = columnName.substring(0, 1).toLowerCase() + columnName.substring(1);
        User user = getByLogin(login);
        String q = "SELECT NEW com.larditrans.service.AutoCompleteResult(e.cellNumber, e." + columnName + ") FROM Entry e where e." + columnName + " LIKE :term and e.owner = :user";
        TypedQuery<AutoCompleteResult> query = em.createQuery(q, AutoCompleteResult.class);
        query.setParameter("term", "%" + term + "%");
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Entry> getSortedEntries(String userLogin, String columnName, String sortOrder, Entry searchEntry) {
        User user = getByLogin(userLogin);
        if (null == user)
            throw new IllegalArgumentException("User not found. =" + userLogin);

        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Entry.class);


        if (searchEntry.getLastName() != null)
            criteria.add(Restrictions.like("lastName", "%"+ searchEntry.getLastName()+"%"));
        if (searchEntry.getFirstName() != null)
            criteria.add(Restrictions.like("firstName", "%"+ searchEntry.getFirstName()+"%"));
        if (searchEntry.getPatronymic() != null)
            criteria.add(Restrictions.like("patronymic", "%"+ searchEntry.getPatronymic()+"%"));
        if (searchEntry.getCellNumber() != null)
            criteria.add(Restrictions.like("cellNumber", "%"+ searchEntry.getCellNumber()+"%"));
        if (searchEntry.getPhoneNumber() != null)
            criteria.add(Restrictions.like("phoneNumber", "%"+ searchEntry.getPhoneNumber()+"%"));
        if (searchEntry.getAddress() != null)
            criteria.add(Restrictions.like("address", "%"+ searchEntry.getAddress()+"%"));
        if (searchEntry.getEmail() != null)
            criteria.add(Restrictions.like("email", "%"+ searchEntry.getEmail()+"%"));

        if (null!=columnName && !columnName.isEmpty())
            {
                columnName = columnName.substring(0, 1).toLowerCase() + columnName.substring(1);
                if (sortOrder.equals("asc"))
                    criteria.addOrder(Order.asc(columnName));
                else if (sortOrder.equals("desc"))
                    criteria.addOrder(Order.desc(columnName));
            }
        return criteria.list();
    }
}
