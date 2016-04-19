package com.larditrans.dao;

import com.larditrans.fileDb.FileDb;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
}
