package com.larditrans.dao;

import com.larditrans.AppConfig;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sergey on 15.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppConfig.class)
@WebAppConfiguration
public class UserDaoImplDBTest {

    @Autowired
    @Qualifier("userDaoImplDB")
    private UserDao userDao;

    private User testUser = new User("test", "test", "Test Test");

    @Test
    public void testAdd() throws Exception {
        userDao.add(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser, testUser);
        userDao.delete(testUser);
    }

    @Test
    public void testDelete() throws Exception {
        userDao.add(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser, testUser);

        userDao.delete(aUser);
        aUser = userDao.getByLogin(aUser.getLogin());
        assertNull(aUser);
    }

    @Test
    public void testUpdate() throws Exception {
        userDao.add(testUser);
        testUser.setPassword("new password");
        userDao.update(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser.getPassword(), testUser.getPassword());
        userDao.delete(testUser);
    }
 }