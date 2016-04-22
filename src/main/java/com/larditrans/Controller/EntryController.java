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
import java.util.*;

/**
 * Created by sergey on 20.04.2016.
 */
@RestController
public class EntryController {
    @Autowired
    private UserService userService;

    private boolean isAuthorized(String userLogin, Integer token) {
        User user = userService.getUserByLogin(userLogin);
        if (null == user || !Tokenizer.getInstance().getToken(userLogin).equals(token))
            return false;
        else
            return true;
    }

    @RequestMapping(value = "/getEntries", method = RequestMethod.POST)
    public String doGetEntries(@RequestParam String userLogin, @RequestParam Integer token,
                               @RequestParam(name = "sidx") String columnName, // column to sort
                               @RequestParam(name = "sord") String sortOrder, // sort direction, desc or asc
                               @RequestParam(defaultValue = "0") Integer rows, // number of rows to display on paginator
                               @RequestParam(defaultValue = "1") Integer page, // current page number
                               HttpServletResponse response) {
        String result = "";
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            int startIndex = (page - 1) * rows;
            int endIndex = page * rows;
            List<Entry> entries = userService.getSortedEntries(userLogin, columnName, sortOrder, startIndex, endIndex);
//            entries = new HashSet<>();
//            entries.add(new Entry("Иванов", "Иван", "Иванович", "+385243432452", "+380445456456", "ул. Крещатик", "ivanov@gmail.com"));
//            entries.add(new Entry("Петров", "Петр", "Иванович", "+380665465465", "+380445456456", "ул. Крещатик", "ivanov@gmail.com"));
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.page = page;
            jsonResponse.records = userService.getEntriesCount(userLogin);
            jsonResponse.rows = (Entry[]) entries.toArray(new Entry[0]);
            jsonResponse.total = userService.getEntriesCount(userLogin) / rows + 1;
            Gson gson = new GsonBuilder().create();
            result = gson.toJson(jsonResponse);
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
    ) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
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
    ) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            userService.updateEntry(userLogin, new Entry(lastName, firstName, patronymic, correctedCellNumber, phoneNumber, address, email));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @RequestMapping(value = "/deleteEntry", method = RequestMethod.POST)
    public void doDeleteEntry(@RequestParam String userLogin, @RequestParam Integer token, HttpServletResponse response, @RequestParam(name = "id") String cellNumber) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            String correctedCellNumber = cellNumber.replaceAll("\\.|\\(|\\)|\\s|-", "");
            userService.deleteEntry(userLogin, userService.getEntryByCellNumber(userLogin, correctedCellNumber));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String doSearch(@RequestParam String userLogin, @RequestParam Integer token,
                                @RequestParam String columnName, @RequestParam String term,
                                HttpServletResponse response) {
        if (!isAuthorized(userLogin, token))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            List<Entry> searchResult = userService.search(userLogin, columnName, term);
            Entry[] rows = (Entry[]) searchResult.toArray(new Entry[0]);
            Gson gson = new GsonBuilder().create();
            String result = gson.toJson(rows);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            return result;
        }
        return null;
    }

    private class JsonResponse {
        Integer records;
        Integer page;
        Integer total;
        Entry[] rows;
    }

}


