package com.larditrans.controller;

import com.larditrans.dao.UserDao;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sergey on 14.04.2016.
 */
@Controller("mainController")
public class MainController {

    @Autowired
    @Qualifier("userDaoImplDb")
    private UserDao userDao;

    @RequestMapping("/add")
    public void add()
    {
        userDao.add(new User("Вася", "Пупкин", "дурак"));
    }

//    @RequestMapping("/viewEntries")
//    public ModelAndView viewEntries()
//    {
//        List<Entry> entries;
//        entries = entryDao.getAll(new User());
//        return new ModelAndView("viewrecords", "entries", entries);
//    }
}
