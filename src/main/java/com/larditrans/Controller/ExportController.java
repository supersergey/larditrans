package com.larditrans.controller;

import com.larditrans.dao.UserDao;
import com.larditrans.dao.UserDaoImplDb;
import com.larditrans.dao.UserDaoImplFile;
import com.larditrans.fileDb.FileDb;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import com.larditrans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by sergey on 24.04.2016.
 */
@RestController
public class ExportController {

    @Autowired
    @Qualifier("userDaoImplDb")
    private UserDao userDao;

    @Autowired
    private FileDb fileDb;

    @RequestMapping("/export")
    public void doExport() throws Exception
    {
        User user = new User("superz600", "123456", "Test User");
        userDao.add(user);
        User currentUser = fileDb.readFromFile("superz600");
        user.setEntries(currentUser.getEntries());
        for (Entry e : user.getEntries())
            e.setOwner(user);

        userDao.update(user);
    }

}
