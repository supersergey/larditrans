package com.larditrans.dao;

import com.larditrans.service.AutoCompleteResult;
import com.larditrans.model.Entry;
import com.larditrans.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by sergey on 14.04.2016.
 */
public interface UserDao {
    User add(User user);
    void delete(User user);
    User update(User user);
    User getByLogin(String login);

    List<AutoCompleteResult> autoComplete(String login, String columnName, String term);

    User addEntry(String userLogin, Entry entry);
    Entry updateEntry(String userLogin, Entry newEntry);
    Set<Entry> getAllEntries(String userLogin);
    Entry getEntryByCellNumber(String userLogin, String cellNumber);
    void deleteEntry(String userLogin, Entry entry);
    List<Entry> getSortedEntries(String userLogin, String columnName, final String sortOrder, Entry searchEntry);
    int getEntriesCount(String login);

    }
