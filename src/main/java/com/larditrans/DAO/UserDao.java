package com.larditrans.DAO;

import com.larditrans.DAO.model.User;

import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
public interface UserDao {
    void add(User user);
    void delete(User user);
    void update(User user);
    List<User> getAll();
}
