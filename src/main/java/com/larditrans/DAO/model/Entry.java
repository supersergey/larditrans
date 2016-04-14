package com.larditrans.DAO.model;

import javax.persistence.*;

/**
 * Created by sergey on 14.04.2016.
 */
@Entity
public class Entry {
    @Id
    @GeneratedValue
    private long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String cellNumber;

    private String phoneNumber;

    private String address;

    private String email;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;
}
