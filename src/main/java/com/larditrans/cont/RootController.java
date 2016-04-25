package com.larditrans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sergey on 25.04.2016.
 */
@Controller
public class RootController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/login.html";
    }
}
