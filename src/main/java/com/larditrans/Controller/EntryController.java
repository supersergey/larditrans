package com.larditrans.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import com.larditrans.service.UserService;
import com.larditrans.tokenizer.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sergey on 20.04.2016.
 */
@RestController
public class EntryController {
    @Autowired
    private UserService userService;

    private boolean isAuthorized(String userLogin, Integer token)
    {
        User user = userService.getUserByLogin(userLogin);
        if (null == user || !Tokenizer.getInstance().getToken(userLogin).equals(token))
            return false;
        else
            return true;
    }

    @RequestMapping(value = "/getEntries", method = RequestMethod.POST)
    public String doGetEntries(@RequestParam String userLogin, @RequestParam Integer token, HttpServletResponse response) {
        String result = "";
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            Set<Entry> entries = userService.getAllEntries(userLogin);
//            entries = new HashSet<>();
//            entries.add(new Entry("Иванов", "Иван", "Иванович", "+385243432452", "+380445456456", "ул. Крещатик", "ivanov@gmail.com"));
//            entries.add(new Entry("Петров", "Петр", "Иванович", "+380665465465", "+380445456456", "ул. Крещатик", "ivanov@gmail.com"));
            Entry[] entriesArray = new Entry[entries.size()];
            entriesArray = (Entry[]) entries.toArray(new Entry[0]);
            Gson gson = new GsonBuilder().create();
            result = gson.toJson(entriesArray);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return result;
    }

    @RequestMapping(value = "/addEntry", method = RequestMethod.POST)
    public void doAddEntry(@RequestParam String userLogin, @RequestParam Integer token, HttpServletResponse response,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String patronymic,
                             @RequestParam String cellNumber,
                             @RequestParam(required = false) String phoneNumber,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String address
                             )
    {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
        {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            userService.addEntry(userLogin, new Entry(lastName, firstName, patronymic, correctedCellNumber, phoneNumber, address, email));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @RequestMapping(value = "/editEntry", method = RequestMethod.POST)
    public void doEditEntry(@RequestParam String userLogin, @RequestParam Integer token, HttpServletResponse response,
                           @RequestParam String lastName,
                           @RequestParam String firstName,
                           @RequestParam String patronymic,
                           @RequestParam String cellNumber,
                           @RequestParam(required = false) String phoneNumber,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String address
    )
    {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
        {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            userService.updateEntry(userLogin, new Entry(lastName, firstName, patronymic, correctedCellNumber, phoneNumber, address, email));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @RequestMapping(value = "/deleteEntry", method = RequestMethod.POST)
    public void doDeleteEntry(@RequestParam String userLogin, @RequestParam Integer token, HttpServletResponse response, @RequestParam(name = "id") String cellNumber)
    {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
        {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            userService.deleteEntry(userLogin, userService.getEntryByCellNumber(userLogin, correctedCellNumber));
        }
    }


}


