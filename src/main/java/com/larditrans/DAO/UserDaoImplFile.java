package com.larditrans.dao;

import com.larditrans.fileDb.FileDb;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sergey on 15.04.2016.
 */
@Component
public class UserDaoImplFile implements UserDao {

    @Autowired
    private FileDb fileDb;

    @Override
    public User add(User user) {

        if (null == user || null == user.getLogin() || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");

        if (fileDb.exists(user.getLogin()))
            throw new IllegalArgumentException("User with the specified login already exists. = " + user.getLogin());

        try {
            fileDb.writeToFile(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(User user) {

        if (null == user || null == user.getLogin() || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");
        fileDb.deleteFile(user);
    }

    @Override
    public User update(User user) {
        if (null == user || null == user.getLogin() || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");
        if (fileDb.exists(user.getLogin()))
            delete(user);
        try {
            fileDb.writeToFile(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User getByLogin(String login) {
        if (null == login || login.isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");
        if (!fileDb.exists(login)) return null;
        User result = null;
        try {
            result = fileDb.readFromFile(login);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int getEntriesCount(String login)
    {
        User user = getByLogin(login);
        return (null == user.getEntries()) ? 0 : user.getEntries().size();
    }

    public List<Entry> search(String login, String columnName, String term)
    {
        if (null == login || login.isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed. = " + login);
        if (!fileDb.exists(login))
            throw new IllegalArgumentException("User not found. = " + login);
        List<Entry> result = new ArrayList<>();
        User user = getByLogin(login);
        if (null == user)
            return null;
        else {
            for (Entry e : user.getEntries()) {
                try
                {
                    String s = (String) e.getClass().getMethod("get" + columnName).invoke(e);
                    if (s.contains(term))
                        result.add(e);
                }
                catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {}
            }
            return result;
        }
    }
}
