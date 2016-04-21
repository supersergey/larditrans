package com.larditrans.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.larditrans.model.User;
import com.larditrans.service.UserService;
import com.larditrans.tokenizer.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by sergey on 19.04.2016.
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public Integer doLogin(@RequestParam String userLogin, @RequestParam String password, HttpServletResponse response) {

        Integer token = 0;
        User user = userService.getUserByLogin(userLogin);
        if (null == user)
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else if (!password.equals(user.getPassword()))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
        {
            response.setStatus(HttpServletResponse.SC_OK);
            token = Tokenizer.getInstance().getToken(userLogin);
        }
        return token;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void doRegister(@RequestParam String userLogin, @RequestParam String password, @RequestParam String fullName, HttpServletResponse response) {
        User user = new User(userLogin, password, fullName);
        if (userService.getUserByLogin(userLogin) != null)
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        userService.addUser(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
