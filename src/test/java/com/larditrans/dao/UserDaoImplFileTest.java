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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by sergey on 15.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppConfig.class)
@WebAppConfiguration
public class UserDaoImplFileTest {

    private User testUser = new User("test", "test", "Test Test");

    @Autowired
    @Qualifier("userDaoImplFile")
    private UserDao userDao;

    @Test
    public void testAdd() throws Exception {
        userDao.add(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser, testUser);
        userDao.delete(testUser);
    }

    @Test
    public void testUpdate() throws Exception {
        userDao.add(testUser);
        testUser.setFullName("Иванов");
        userDao.update(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser.getFullName(), testUser.getFullName());
        userDao.delete(testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdd_UserAlreadyExist() throws Exception {
        userDao.add(testUser);
        userDao.add(testUser);
        userDao.delete(testUser);
    }

    @Test
    public void testDelete() throws Exception {
        userDao.add(testUser);
        assertNotNull(userDao.getByLogin(testUser.getLogin()));
        userDao.delete(testUser);
        assertNull(userDao.getByLogin(testUser.getLogin()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_UserDoesNotExist() throws Exception {
        userDao.delete(testUser);
    }
}