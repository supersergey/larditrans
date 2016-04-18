package com.larditrans.dao;

import com.larditrans.AppConfig;
import com.larditrans.PersistenceJPAConfig;
import com.larditrans.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.Assert.*;

/**
 * Created by sergey on 15.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PersistenceJPAConfig.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class, AppConfig.class}, loader = AnnotationConfigContextLoader.class)
@EnableWebMvc
@Transactional
@EnableAutoConfiguration
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