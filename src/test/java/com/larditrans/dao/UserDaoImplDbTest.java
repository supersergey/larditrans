package com.larditrans.dao;

import com.larditrans.AppConfig;
import com.larditrans.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by sergey on 15.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppConfig.class)
@Transactional
@WebAppConfiguration
public class UserDaoImplDbTest {

    @Autowired
    @Qualifier("userDaoImplDb")
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