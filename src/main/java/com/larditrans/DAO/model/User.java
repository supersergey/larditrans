package com.larditrans.DAO.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sergey on 14.04.2016.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String login;

    private String password;

    private String fullName;
    
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Entry> entries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public User(String login, String password, String fullName, List<Entry> entries) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.entries = entries;
    }

    public User() {
    }
}
