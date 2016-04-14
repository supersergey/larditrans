package com.larditrans.DAO;

import com.larditrans.DAO.model.Entry;

import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
public interface EntryDao {
    void add(Entry entry);
    void delete(Entry entry);
    void update(Entry entry);
    List<Entry> getAll();
}
