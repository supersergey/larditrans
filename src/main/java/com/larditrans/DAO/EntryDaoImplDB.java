package com.larditrans.DAO;

import com.larditrans.DAO.model.Entry;
import com.larditrans.DAO.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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
    public void add(Entry entry) {

    }

    @Override
    public void delete(Entry entry) {

    }

    @Override
    public void update(Entry entry) {

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
