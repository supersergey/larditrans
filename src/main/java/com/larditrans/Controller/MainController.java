package com.larditrans.controller;

import com.larditrans.dao.EntryDao;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
@Controller("mainController")
public class MainController {

//    @Autowired
//    private EntryDao entryDao;
//
//    @RequestMapping("/viewEntries")
//    public ModelAndView viewEntries()
//    {
//        List<Entry> entries;
//        entries = entryDao.getAll(new User());
//        return new ModelAndView("viewrecords", "entries", entries);
//    }
}
