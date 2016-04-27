package com.larditrans.dao;

import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * Created by sergey on 26.04.2016.
 */

/* Implements common operations with entries that do not require direct
   database calls. These operations are common to any Dao implementation.
 */

public abstract class AbstractUserDao implements UserDao{

    public Set<Entry> getAllEntries(String userLogin) {
        User currentUser = getByLogin(userLogin);
        if (currentUser == null) {
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        }
        return currentUser.getEntries();
    }

    public Entry getEntryByCellNumber(String userLogin, String cellNumber) {
        User currentUser = getByLogin(userLogin);

        if (currentUser == null)
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        for (Entry e : currentUser.getEntries())
            if (e.getCellNumber().equals(cellNumber))
                return e;
        return null;
    }

    public int getEntriesCount(String userLogin) {
        return getEntriesCount(userLogin);
    }

}
