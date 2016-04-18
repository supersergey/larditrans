package com.larditrans.service;

import com.larditrans.AppConfig;
import com.larditrans.dao.EntryDao;
import com.larditrans.dao.UserDao;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by sergey on 16.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppConfig.class)
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    // @Qualifier("userDaoImplDB")
    private UserDao userDao;

    @Autowired
    private EntryDao entryDao;

    private User user = new User("test", "test", "test");
    Entry entry = new Entry("Иванов", "Иван", "Иванович", "+380959998877", "+380441112233", "Ул. Крещатик", "ivanov@ivanov.com");

    @Before
    public void addUser()
    {
        userDao.add(user);
    }

    @After
    public void removeUser()
    {
        userDao.delete(user);
    }

//    @Test
//    public void testAddEntry() throws Exception {
//        int startCount = userService.getEntriesCount(user.getLogin());
//        assertEquals(startCount, 0);
//        userService.addEntry(user.getLogin(), entry);
//        assertEquals(userService.getEntriesCount(user.getLogin()), startCount + 1);
//
//        Entry aEntry = userService.getEntryById(user.getLogin(), entry.getId());
//        assertNotNull(aEntry);
//        userService.deleteEntry(user.getLogin(), aEntry);
//        assertEquals(userService.getEntriesCount(user.getLogin()), 0);
//    }
//
//    @Test
//    public void testUpdateEntry() throws Exception {
//        userService.addEntry(user.getLogin(), entry);
//        List<Entry> entries = userService.getAllEntries(user.getLogin());
//        assertNotNull(entries);
//        assertTrue(entries.size() > 0);
//        Entry aEntry = entries.get(0);
//        aEntry.setFirstName("Петр");
//        userService.updateEntry(user.getLogin(), aEntry);
//        Entry updatedEntry = userService.getEntryById(user.getLogin(), aEntry.getId());
//        assertNotNull(updatedEntry);
//        assertEquals(aEntry.getFirstName(), updatedEntry.getFirstName());
//    }

    @Test
    public void testEntryDao() throws Exception {
        Entry addedEntry = entryDao.add(user, entry);
        for (Entry e : user.getEntries())
            System.out.println(e.getId() + " " + e.getFirstName());
        addedEntry.setFirstName("Петр");
        entryDao.update(user, entry);
        for (Entry e : user.getEntries())
            System.out.println(e.getId() + " " + e.getFirstName());
    }
}