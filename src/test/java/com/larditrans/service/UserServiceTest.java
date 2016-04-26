package com.larditrans.service;

import com.larditrans.AppConfig;
import com.larditrans.dao.UserDao;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sergey on 16.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppConfig.class)
@Transactional
@WebAppConfiguration
public class UserServiceTest {

    static
    {
        System.setProperty("myproperty", "foo");
    }

    @Autowired
    private DaoProviderService daoProviderService;

    @Autowired
    private UserService userService;

    private UserDao userDao;

    @PostConstruct
    public void postConstruct() {
        userDao = daoProviderService.provideUserDao();
    }

    private User user = new User("testuser", "testuser", "Test Test");
    Entry entry = new Entry("Иванов", "Иван", "Иванович", "+380959998877", "+380441112233", "Ул. Крещатик", "ivanov@ivanov.com");

    @Before
    public void addUser() {
        userDao.add(user);
    }

    @After
    public void removeUser() {
        userDao.delete(user);
    }

    @Test
    public void testAddEntry() throws Exception {

        HashSet<Entry> testSet = new HashSet<>();
        testSet.add(entry);
        assertTrue(testSet.contains(entry));

        int startCount = userService.getEntriesCount(user.getLogin());
        assertEquals(startCount, 0);

        userService.addEntry(user.getLogin(), entry);
        assertEquals(userService.getEntriesCount(user.getLogin()), startCount + 1);

        Entry aEntry = userService.getEntryByCellNumber(user.getLogin(), entry.getCellNumber());
        assertNotNull(aEntry);

        userService.deleteEntry(user.getLogin(), aEntry);
        assertEquals(userService.getEntriesCount(user.getLogin()), 0);
    }

    @Test
    public void testUpdateEntry() throws Exception {
        userService.addEntry(user.getLogin(), entry);
        Set<Entry> entries = userService.getAllEntries(user.getLogin());
        assertNotNull(entries);
        assertTrue(entries.size() > 0);

        Entry aEntry = userService.getEntryByCellNumber(user.getLogin(), entry.getCellNumber());
        aEntry.setFirstName("Петр");
        userService.updateEntry(user.getLogin(), aEntry);
        Entry updatedEntry = userService.getEntryByCellNumber(user.getLogin(), entry.getCellNumber());
        assertNotNull(updatedEntry);
        assertEquals(aEntry.getFirstName(), updatedEntry.getFirstName());
    }
}
