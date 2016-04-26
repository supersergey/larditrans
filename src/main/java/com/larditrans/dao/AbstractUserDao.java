package com.larditrans.dao;

import com.larditrans.model.Entry;
import com.larditrans.model.User;

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

    public User addEntry(String userLogin, Entry entry) {
        if (userLogin == null || userLogin.equals("")) {
            throw new IllegalArgumentException("Empty user login is not allowed.");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Empty entry is not allowed.");
        }

        if (!entry.isValid())
            throw new IllegalArgumentException("Entry fields are not valid.");

        User currentUser = getByLogin(userLogin);

        if (currentUser == null) {
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        }

        entry.setOwner(currentUser);
        currentUser.getEntries().add(entry);

        update(currentUser);

        return currentUser;
    }

    public Entry updateEntry(String userLogin, Entry newEntry) {
        if (userLogin == null || userLogin.equals("")) {
            throw new IllegalArgumentException("Empty user login is not allowed.");
        }
        if (newEntry == null) {
            throw new IllegalArgumentException("Empty entry is not allowed.");
        }

        if (!newEntry.isValid())
            throw new IllegalArgumentException("Entry fields are not valid.");

        User currentUser = getByLogin(userLogin);

        if (currentUser == null) {
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        }

        Set<Entry> entries = currentUser.getEntries();
        Entry changeEntry = null;

        if (null == entries)
            throw new IllegalArgumentException("The user has no entries. Nothing to update.");

        for (Entry e : entries)
            if (e.getCellNumber().equals(newEntry.getCellNumber())) {
                changeEntry = e;
                break;
            }
        currentUser.getEntries().remove(changeEntry);
        currentUser.getEntries().add(newEntry);
        update(currentUser);
        return newEntry;
    }

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

    public void deleteEntry(String userLogin, Entry entry) {
        User currentUser = getByLogin(userLogin);

        Entry entryToDelete = getEntryByCellNumber(userLogin, entry.getCellNumber());

        if (null == entryToDelete)
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);

        if (!currentUser.getEntries().contains(entryToDelete))
            throw new IllegalArgumentException("Entry is not found." + userLogin);
        currentUser.getEntries().remove(entryToDelete);
        update(currentUser);
    }

    public int getEntriesCount(String userLogin) {
        return getEntriesCount(userLogin);
    }

    public List<Entry> getSortedEntries(String userLogin, String columnName, String sortOrder, Entry searchEntry) {

        return getSortedEntries(userLogin, columnName, sortOrder, searchEntry);
    }

}
