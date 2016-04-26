package com.larditrans.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.larditrans.dao.UserDao;
import com.larditrans.service.AutoCompleteResult;
import com.larditrans.model.Entry;
import com.larditrans.model.User;
import com.larditrans.service.UserService;
import com.larditrans.tokenizer.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by sergey on 20.04.2016.
 */
@RestController
public class EntryController {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userDaoImplDb")
    private UserDao userDao;

    private boolean isAuthorized(String userLogin, String token) {
        User user = userService.getUserByLogin(userLogin);
        if (null == user || !Tokenizer.getInstance().getToken(userLogin).equals(token))
            return false;
        else
            return true;
    }

    @RequestMapping(value = "/getEntries", method = RequestMethod.POST)
    public String doGetEntries(@RequestParam String userLogin, @RequestParam String token,
                               @RequestParam(name = "sidx", required = false) String columnName, // column to sort
                               @RequestParam(name = "sord", required = false) String sortOrder, // sort direction, desc or asc
                               @RequestParam(defaultValue = "0") Integer rows, // number of rows to display on paginator
                               @RequestParam(defaultValue = "1") Integer page, // current page number
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String patronymic,
                               @RequestParam(required = false) String cellNumber,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) String email,
                               HttpServletResponse response) {
        String result = "";
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
//            int startIndex = (page - 1) * rows;
//            int endIndex = page * rows;
            Entry searchEntry = new Entry(lastName, firstName, patronymic, cellNumber, phoneNumber, address, email);
            List<Entry> entries = userService.getSortedEntries(userLogin, columnName, sortOrder,
                    searchEntry);

            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.total = entries.size() / rows + 1;
            jsonResponse.records = entries.size();
            if (rows < entries.size()) {
                int startIndex = (page - 1) * rows;
                int endIndex = page * rows;
                if (endIndex > entries.size())
                    endIndex = entries.size();
                entries = entries.subList(startIndex, endIndex);
            }

            for (Entry e : entries)
                e.setOwner(null);
            jsonResponse.page = page;
            jsonResponse.rows = entries;
            Gson gson = new GsonBuilder().create();
            result = gson.toJson(jsonResponse);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return result;
    }

    @RequestMapping(value = "/addEntry", method = RequestMethod.POST)
    public void doAddEntry(@RequestParam String userLogin, @RequestParam String token, HttpServletResponse response,
                           @RequestParam String lastName,
                           @RequestParam String firstName,
                           @RequestParam String patronymic,
                           @RequestParam String cellNumber,
                           @RequestParam(required = false) String phoneNumber,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String address
    ) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            if (null == userService.getEntryByCellNumber(userLogin, cellNumber)) {
                userService.addEntry(userLogin, new Entry(lastName, firstName, patronymic, correctedCellNumber, phoneNumber, address, email));
                response.setStatus(HttpServletResponse.SC_OK);
            } else
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @RequestMapping(value = "/editEntry", method = RequestMethod.POST)
    public void doEditEntry(@RequestParam String userLogin, @RequestParam String token, HttpServletResponse response,
                            @RequestParam String lastName,
                            @RequestParam String firstName,
                            @RequestParam String patronymic,
                            @RequestParam String cellNumber,
                            @RequestParam(required = false) String phoneNumber,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) String address
    ) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            if (null == userService.getEntryByCellNumber(userLogin, cellNumber)) {
                userService.updateEntry(userLogin, new Entry(lastName, firstName, patronymic, correctedCellNumber, phoneNumber, address, email));
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    @RequestMapping(value = "/deleteEntry", method = RequestMethod.POST)
    public void doDeleteEntry(@RequestParam String userLogin, @RequestParam String token, HttpServletResponse response, @RequestParam(name = "id") String cellNumber) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            userService.deleteEntry(userLogin, userService.getEntryByCellNumber(userLogin, correctedCellNumber));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @RequestMapping(value = "/autoComplete", method = RequestMethod.POST)
    public String doSearch(@RequestParam String userLogin, @RequestParam String token,
                           @RequestParam String columnName, @RequestParam String term,
                           HttpServletResponse response) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            List<AutoCompleteResult> autoCompleteResult = userService.autoComplete(userLogin, columnName, term);
            if (null == autoCompleteResult || autoCompleteResult.isEmpty())
                return null;
            else {
                AutoCompleteResult[] rows = (AutoCompleteResult[]) autoCompleteResult.toArray(new AutoCompleteResult[0]);
                Gson gson = new GsonBuilder().create();
                String result = gson.toJson(rows);
                response.setStatus(HttpServletResponse.SC_OK);
                return result;
            }
        }
        return null;
    }

    private class JsonResponse {
        Integer records;
        Integer page;
        Integer total;
        List<Entry> rows;
    }
}
