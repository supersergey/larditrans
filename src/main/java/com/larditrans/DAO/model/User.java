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
}
