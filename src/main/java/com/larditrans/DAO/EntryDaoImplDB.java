package com.larditrans.dao;

import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
@Component
public class EntryDaoImplDB implements EntryDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Entry add(User user, Entry entry) {
        entry.setOwner(user);
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.persist(entry);
        et.commit();
        return entry;
    }

    @Override
    public Entry get(long id) {
        final Query query = entityManager.createQuery("SELECT e FROM Entry e where e.owner.id = :id", Entry.class);
        query.setParameter("id", id);
        return (Entry) query.getSingleResult();
    }

    @Override
    public void delete(Entry entry) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.remove(entry);
        et.commit();
    }

    @Override
    public Entry update(User user, Entry entry) {
        entry.setOwner(user);
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        Entry updatedEntry = entityManager.merge(entry);
        et.commit();
        return updatedEntry;
    }

    @Override
    public List<Entry> getAll(User owner)
    {
        owner.setId(1L); // for testing purposes
        final Query query = entityManager.createQuery("SELECT e FROM Entry e where e.owner.id = :owner", Entry.class);
        query.setParameter("owner", owner.getId());
        return (List<Entry>) query.getResultList();
    }
}
