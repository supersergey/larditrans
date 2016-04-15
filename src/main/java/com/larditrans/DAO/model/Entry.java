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
    @JoinColumn(name = "user_id")
    private User owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Entry(String lastName, String firstName, String patronymic, String cellNumber, String phoneNumber, String address, String email, User owner) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.cellNumber = cellNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.owner = owner;
    }

    public Entry() {
    }
}
