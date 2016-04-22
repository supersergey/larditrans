package com.larditrans.dao;

import com.larditrans.model.Entry;
import com.larditrans.model.User;

import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
public interface UserDao {
    User add(User user);
    void delete(User user);
    User update(User user);
    User getByLogin(String login);
    int getEntriesCount(String login);
    List<Entry> search(String login, String columnName, String term);
    }
