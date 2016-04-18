package com.larditrans.service;

import com.larditrans.dao.EntryDao;
import com.larditrans.dao.UserDao;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Set;

/**
 * Created by sergey on 16.04.2016.
 */
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EntryDao entryDao;

    public User addEntry(String userLogin, Entry entry) {
        User currentUser = userDao.getByLogin(userLogin);
        if (null != currentUser && null != entry) {
            entry.setOwner(currentUser);
            currentUser.getEntries().add(entry);
            userDao.update(currentUser);
        }
        return currentUser;
    }

    public Entry updateEntry(String userLogin, Entry newEntry) {
        User currentUser = userDao.getByLogin(userLogin);
        if (null != currentUser) {
            Set<Entry> entries = currentUser.getEntries();
            Entry changeEntry = null;
            if (null != entries) {
                for (Entry e : entries)
                    if (e.getId() == newEntry.getId()) {
                        changeEntry = e;
                        break;
                    }
            }
            currentUser.getEntries().remove(changeEntry);
            currentUser.getEntries().add(newEntry);
            userDao.update(currentUser);
        }
        return newEntry;
    }

    public Set<Entry> getAllEntries(String userLogin) {
        User currentUser = userDao.getByLogin(userLogin);
        if (null != currentUser) {
            return currentUser.getEntries();
        } else
            return null;
    }

    public Entry getEntryById(String userLogin, Long id) {
        User currentUser = userDao.getByLogin(userLogin);
        if (null != currentUser) {
            for (Entry e : currentUser.getEntries())
                if (e.getId() == id)
                    return e;
        }
        return null;
    }

    public void deleteEntry(String userLogin, Entry entry) {
        User currentUser = userDao.getByLogin(userLogin);
        if (null != currentUser)
            if (null != currentUser.getEntries()) {
                currentUser.getEntries().remove(entry);
                userDao.update(currentUser);
            }
    }

    public int getEntriesCount(String userLogin) {
        Set<Entry> entries = getAllEntries(userLogin);
        return null == entries ? 0 : entries.size();
    }
}
