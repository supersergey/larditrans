package com.larditrans.service;

import com.larditrans.dao.UserDao;
import com.larditrans.dao.UserDaoImplFile;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

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

    public User addUser(User user) {
        userDao.add(user);
        return user;
    }

    public User getUserByLogin(String login) {
        return userDao.getByLogin(login);
    }

    public List<AutoCompleteResult> autoComplete(String userLogin, String columnName, String term)
    {
        return userDao.autoComplete(userLogin, columnName, term);
    }

    public User addEntry(String userLogin, Entry entry)
    {
        return userDao.addEntry(userLogin, entry);
    }

    public Entry updateEntry(String userLogin, Entry newEntry)
    {
        return userDao.updateEntry(userLogin, newEntry);
    }

    public Set<Entry> getAllEntries(String userLogin)
    {
        return userDao.getAllEntries(userLogin);
    }

    public Entry getEntryByCellNumber(String userLogin, String cellNumber) {
        return userDao.getEntryByCellNumber(userLogin, cellNumber);
    }

    public void deleteEntry(String userLogin, Entry entry)
    {
        userDao.deleteEntry(userLogin, entry);
    }

    public int getEntriesCount(String userLogin)
    {
        return userDao.getEntriesCount(userLogin);
    }

    public List<Entry> getSortedEntries(String userLogin, String columnName, String sortOrder, Entry searchEntry) {

        return userDao.getSortedEntries(userLogin, columnName, sortOrder, searchEntry);
    }
}
