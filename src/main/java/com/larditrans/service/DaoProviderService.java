package com.larditrans.service;

import com.larditrans.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sergey on 19.04.2016.
 */
@Service
public class DaoProviderService {
    private boolean fileDbEnabled;

    @Autowired
    private UserDao userDaoImplFile;

    @Autowired
    private UserDao userDaoImplDb;

    public UserDao provideUserDao() {
        if (fileDbEnabled) {
            return userDaoImplFile;
        } else {
            return userDaoImplDb;
        }
    }

    public void enableFileDb() {
        fileDbEnabled = true;
    }

    public void disableFileDb() {
        fileDbEnabled = false;
    }

    public boolean isFileDbEnabled() {
        return fileDbEnabled;
    }
}
