package com.larditrans.dao;

import com.larditrans.fileDb.FileDb;
import com.larditrans.service.AutoCompleteResult;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by sergey on 15.04.2016.
 */
@Component
public class UserDaoImplFile extends AbstractUserDao {

    @Autowired
    private FileDb fileDb;

    @Override
    public User add(User user) {

        if (null == user || null == user.getLogin() || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");

        if (fileDb.exists(user.getLogin()))
            throw new IllegalArgumentException("User with the specified login already exists. = " + user.getLogin());

        try {
            fileDb.writeToFile(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(User user) {

        if (null == user || null == user.getLogin() || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");
        if (null == getByLogin(user.getLogin()))
            throw new IllegalArgumentException("Specified user does not exist. = " + user.getLogin());
        fileDb.deleteFile(user);
    }

    @Override
    public User update(User user) {
        if (null == user || null == user.getLogin() || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");
        if (fileDb.exists(user.getLogin()))
            delete(user);
        try {
            fileDb.writeToFile(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User getByLogin(String login) {
        if (null == login || login.isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed.");
        if (!fileDb.exists(login)) return null;
        User result = null;
        try {
            result = fileDb.readFromFile(login);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int getEntriesCount(String login) {
        User user = getByLogin(login);
        return (null == user.getEntries()) ? 0 : user.getEntries().size();
    }

    public List<AutoCompleteResult> autoComplete(String login, String columnName, String term) {
        if (null == login || login.isEmpty())
            throw new IllegalArgumentException("Empty user login is not allowed. = " + login);
        if (!fileDb.exists(login))
            throw new IllegalArgumentException("User not found. = " + login);
        User user = getByLogin(login);
        if (null == user)
            return null;
        else {
            List<AutoCompleteResult> result = new LinkedList<>();
            for (Entry e : user.getEntries()) {
                try {
                    String name = (String) e.getClass().getMethod("get" + columnName).invoke(e);
                    if (null != name) {
                        String id = e.getCellNumber();
                        if (name.contains(term))
                            result.add(new AutoCompleteResult(id, name));
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                }
            }
            return result;
        }
    }

    @Override
    public List<Entry> getSortedEntries(String userLogin, String columnName, final String sortOrder, Entry searchEntry) {
        List<Entry> sortedList = new ArrayList<>(getByLogin(userLogin).getEntries());
        if (!columnName.isEmpty()) {
            final String modifiedColumnName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            Collections.sort(sortedList, new Comparator<Entry>() {
                @Override
                public int compare(Entry e1, Entry e2) {
                    String value1 = "";
                    String value2 = "";
                    try {
                        value1 = (String) e1.getClass().getMethod("get" + modifiedColumnName).invoke(e1);
                        value2 = (String) e2.getClass().getMethod("get" + modifiedColumnName).invoke(e2);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {}
                    if (null != value1 && null != value2)
                        return sortOrder.equals("asc") ? value1.compareTo(value2) : value2.compareTo(value1);
                    else return 0;
                }
            });
        }

        List<Entry> resultList = new LinkedList<>();
        for (Entry e : sortedList) {
            boolean match = true;
            if (null != searchEntry.getLastName() && !e.getLastName().contains(searchEntry.getLastName()))
                match = false;
            if (null != searchEntry.getFirstName() && !e.getFirstName().contains(searchEntry.getFirstName()))
                match = false;
            if (null != searchEntry.getPatronymic() && !e.getPatronymic().contains(searchEntry.getPatronymic()))
                match = false;
            if (null != searchEntry.getCellNumber() && !e.getCellNumber().contains(searchEntry.getCellNumber()))
                match = false;
            if (null != e.getPhoneNumber())
                if (null != searchEntry.getPhoneNumber() && !e.getPhoneNumber().contains(searchEntry.getPhoneNumber()))
                    match = false;
            if (null != e.getAddress())
                if (null != searchEntry.getAddress() && !e.getAddress().contains(searchEntry.getAddress()))
                    match = false;
            if (null != e.getEmail())
                if (null != searchEntry.getEmail() && !e.getEmail().contains(searchEntry.getEmail()))
                    match = false;
            if (match)
                resultList.add(e);
        }
        return resultList;
    }

    public User addEntry(String userLogin, Entry entry) {
        if (userLogin == null || userLogin.equals("")) {
            throw new IllegalArgumentException("Empty user login is not allowed.");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Empty entry is not allowed.");
        }

        if (!entry.isValid())
            throw new IllegalArgumentException("Entry fields are not valid.");

        User currentUser = getByLogin(userLogin);

        if (currentUser == null) {
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        }

        entry.setOwner(currentUser);
        currentUser.getEntries().add(entry);

        update(currentUser);

        return currentUser;
    }

    public Entry updateEntry(String userLogin, Entry newEntry) {
        if (userLogin == null || userLogin.equals("")) {
            throw new IllegalArgumentException("Empty user login is not allowed.");
        }
        if (newEntry == null) {
            throw new IllegalArgumentException("Empty entry is not allowed.");
        }

        if (!newEntry.isValid())
            throw new IllegalArgumentException("Entry fields are not valid.");

        User currentUser = getByLogin(userLogin);

        if (currentUser == null) {
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);
        }

        Set<Entry> entries = currentUser.getEntries();
        Entry changeEntry = null;

        if (null == entries)
            throw new IllegalArgumentException("The user has no entries. Nothing to update.");

        for (Entry e : entries)
            if (e.getCellNumber().equals(newEntry.getCellNumber())) {
                changeEntry = e;
                break;
            }
        currentUser.getEntries().remove(changeEntry);
        newEntry.setOwner(currentUser);
        currentUser.getEntries().add(newEntry);
        update(currentUser);
        return newEntry;
    }

    public void deleteEntry(String userLogin, Entry entry) {
        User currentUser = getByLogin(userLogin);

        Entry entryToDelete = getEntryByCellNumber(userLogin, entry.getCellNumber());

        if (null == entryToDelete)
            throw new IllegalArgumentException("User is not found for the specified login = " + userLogin);

        if (!currentUser.getEntries().contains(entryToDelete))
            throw new IllegalArgumentException("Entry is not found." + userLogin);
        currentUser.getEntries().remove(entryToDelete);
        update(currentUser);
    }

}
