package com.larditrans.dao;

import com.larditrans.model.Entry;
import com.larditrans.model.User;

import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
public interface EntryDao {
    Entry get(long id);
    Entry add(User user, Entry entry);
    void delete(Entry entry);
    Entry update(User user, Entry entry);
    List<Entry> getAll(User owner);
}
