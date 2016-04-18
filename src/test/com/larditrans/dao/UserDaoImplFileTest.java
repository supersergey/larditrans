package com.larditrans.dao;

import com.larditrans.AppConfig;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.constraints.AssertTrue;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

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
    private UserDao testUserDao;

//    @Test
//    public void testAdd() throws Exception {
//        testUserDao.add(testUser);
//        User aUser = testUserDao.getById(testUser.getId());
//        assertEquals(aUser, testUser);
//        testUserDao.delete(testUser);
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//        testUserDao.add(testUser);
//        testUserDao.delete(testUser);
//        User aUser = testUserDao.getById(testUser.getId());
//        assertNull(aUser);
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        testUserDao.add(testUser);
//        testUser.setFullName("Another name");
//        testUserDao.update(testUser);
//        User aUser = testUserDao.getById(testUser.getId());
//        assertEquals(testUser.getFullName(), aUser.getFullName());
//        testUserDao.delete(aUser);
//    }
//
//    @Test
//    public void testGetAll() throws Exception {
//        int count = testUserDao.getAll().size();
//        testUserDao.add(testUser);
//        int newCount = testUserDao.getAll().size();
//        assertEquals(count + 1, newCount);
//        testUserDao.delete(testUser);
//        newCount = testUserDao.getAll().size();
//        assertEquals(count, newCount);
//    }
//
//    @Test
//    public void testGet() throws Exception {
//        testUserDao.add(testUser);
//        User aUser = testUserDao.getById(testUser.getId());
//        assertEquals(aUser, testUser);
//
//        aUser = testUserDao.getByLogin(testUser.getLogin());
//        assertEquals(aUser, testUser);
//
//        List<User> userList1 = testUserDao.getByFullName(testUser.getFullName());
//        testUserDao.add(testUser);
//        List<User> userList2 = testUserDao.getByFullName(testUser.getFullName());
//        assertEquals(userList1.size(), userList2.size());
//        testUserDao.delete(testUser);
//        testUserDao.delete(testUser);
//    }

}