package com.larditrans.dao;

import com.larditrans.AppConfig;
import com.larditrans.model.Entry;
import com.larditrans.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

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


    @Autowired
    @Qualifier("userDaoImplDb")
    private UserDao userDao;

    private User testUser = new User("testuser", "testuser", "Test Test");
    private User testUser_nullLogin = new User(null, "testuser", "Test Test");
    Entry entry = new Entry("Иванов", "Иван", "Иванович", "+380959998877", "+380441112233", "Ул. Крещатик", "ivanov@ivanov.com");

    List<Entry> entryList = new ArrayList();

    {
        entryList.add(new Entry("Шевченко", "Тарас", "Григориевич", "+380631234567", "0441234567", "c. Моринцы, Черкасская область", "sheva@ukr.net"));
        entryList.add(new Entry("Шевчук", "Алекс", "Григориевич", "+380633336644", "", "", ""));
        entryList.add(new Entry("Шевченко", "Игорь", "Петрович", "+380638887700", "", "", ""));
        entryList.add(new Entry("Шевинский", "Александр", "Павлович", "+381214546581", "", "", ""));
    }


    @Test
    public void testAddUser() throws Exception {
        userDao.add(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser, testUser);
        userDao.delete(testUser);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testAddUser_nullLogin() throws Exception {
        userDao.add(testUser_nullLogin);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testAddUser_duplicateUser() throws Exception {
        userDao.add(testUser);
        userDao.add(testUser);
        userDao.delete(testUser);
    }

    @Test
    public void testDeleteUser() throws Exception {
        userDao.add(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser, testUser);

        userDao.delete(aUser);
        aUser = userDao.getByLogin(aUser.getLogin());
        assertNull(aUser);
    }

    @Test
    public void testUpdateUser() throws Exception {
        userDao.add(testUser);
        testUser.setPassword("new password");
        userDao.update(testUser);
        User aUser = userDao.getByLogin(testUser.getLogin());
        assertEquals(aUser.getPassword(), testUser.getPassword());
        userDao.delete(testUser);
    }

    @Test
    public void testSort() {
        userDao.add(testUser);
        for (Entry e : entryList) {
            e.setOwner(testUser);
            testUser.getEntries().add(e);
        }
        entry.setOwner(testUser);
        testUser.getEntries().add(entry);
        Entry searchEntry = new Entry("Шев", "", "", "", "", "", "");
        List<Entry> entries = userDao.getSortedEntries(testUser.getLogin(), "firstName", "asc", searchEntry);
        assertEquals(4, entries.size());
        assertEquals("Алекс", entries.get(0).getFirstName());

        searchEntry = new Entry("Шевч", "", "", "", "", "", "");
        entries = userDao.getSortedEntries(testUser.getLogin(), "firstName", "desc", searchEntry);
        assertEquals(3, entries.size());

        searchEntry = new Entry("Шев", "", "", "121", "", "", "");
        entries = userDao.getSortedEntries(testUser.getLogin(), "firstName", "desc", searchEntry);
        assertEquals(1, entries.size());
        userDao.delete(testUser);
    }
}