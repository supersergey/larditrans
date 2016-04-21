package com.larditrans.controller;

/**
 * Created by sergey on 19.04.2016.
 */
public class LoginForm {

    public String userlogin;
    public String password;

    public LoginForm(String userlogin, String password) {
        this.userlogin = userlogin;
        this.password = password;
    }

    public LoginForm() {
    }

    public String getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
