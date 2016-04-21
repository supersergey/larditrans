package com.larditrans.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by sergey on 14.04.2016.
 */
@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Expose
    @Column(nullable = false)
    private String lastName;

    @Expose
    @Column(nullable = false)
    private String firstName;

    @Expose
    @Column(nullable = false)
    private String patronymic;

    @Expose
    @Column(unique = true, nullable = false)
    private String cellNumber;

    @Expose
    private String phoneNumber;

    @Expose
    private String address;

    @Expose
    private String email;

    @ManyToOne
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

    public Entry(String lastName, String firstName, String patronymic, String cellNumber, String phoneNumber, String address, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.cellNumber = cellNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public Entry() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (!lastName.equals(entry.lastName)) return false;
        if (!firstName.equals(entry.firstName)) return false;
        if (!patronymic.equals(entry.patronymic)) return false;
        if (!cellNumber.equals(entry.cellNumber)) return false;
        return !(owner != null ? !owner.equals(entry.owner) : entry.owner != null);

    }

    @Override
    public int hashCode() {
        int result = 31 + lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + patronymic.hashCode();
        result = 31 * result + cellNumber.hashCode();
        return result;
    }
}
