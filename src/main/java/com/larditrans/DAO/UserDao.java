package com.larditrans.dao;

import com.larditrans.model.User;

/**
 * Created by sergey on 14.04.2016.
 */
public interface UserDao {
    User add(User user);
    void delete(User user);
    User update(User user);
    User getByLogin(String login);
}
