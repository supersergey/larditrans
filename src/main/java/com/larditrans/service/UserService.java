package com.larditrans.service;

import com.larditrans.dao.UserDao;
import com.larditrans.dao.UserDaoImplFile;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by sergey on 16.04.2016.
 */
@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    private DaoProviderService daoProviderService;

    @PostConstruct
    public void postConstruct() {
        userDao = daoProviderService.provideUserDao();
    }

    public User addEntry(String userLogin, Entry entry) {
        if (userLogin == null || userLogin.equals("")) {
            throw new IllegalArgumentException("Empty user login is not allowed.");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Empty entry is not allowed.");
        }

        User currentUser = userDao.getByLogin(userLogin);

        if (currentUser == null) {
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        }

        entry.setOwner(currentUser);
        currentUser.getEntries().add(entry);

        if (userDao instanceof UserDaoImplFile)
            userDao.update(currentUser);

        return currentUser;
    }

    public Entry updateEntry(String userLogin, Entry newEntry) {
        if (userLogin == null || userLogin.equals("")) {
            throw new IllegalArgumentException("Empty user login is not allowed.");
        }
        if (newEntry == null) {
            throw new IllegalArgumentException("Empty entry is not allowed.");
        }

        User currentUser = userDao.getByLogin(userLogin);

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
        if (userDao instanceof UserDaoImplFile)
            userDao.update(currentUser);
        return newEntry;
    }

    public Set<Entry> getAllEntries(String userLogin) {
        User currentUser = userDao.getByLogin(userLogin);
        if (currentUser == null) {
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        }
        return currentUser.getEntries();
    }

    public Entry getEntryByCellNumber(String userLogin, String cellNumber) {
        User currentUser = userDao.getByLogin(userLogin);

        if (currentUser == null)
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        for (Entry e : currentUser.getEntries())
            if (e.getCellNumber().equals(cellNumber))
                return e;
        return null;
    }

    public void deleteEntry(String userLogin, Entry entry) {
        User currentUser = userDao.getByLogin(userLogin);

        Entry entryToDelete = getEntryByCellNumber(userLogin, entry.getCellNumber());

        if (null == entryToDelete)
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);

        if (!currentUser.getEntries().contains(entryToDelete))
            throw new IllegalArgumentException("Entry is not found." + userLogin);
        currentUser.getEntries().remove(entryToDelete);
    }

    public int getEntriesCount(String userLogin) {
        Set<Entry> entries = getAllEntries(userLogin);
        return null == entries ? 0 : entries.size();
    }
}
