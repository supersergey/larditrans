package com.larditrans.model;

import javax.persistence.*;

/**
 * Created by sergey on 14.04.2016.
 */
@Entity
public class Entry {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String patronymic;

    @Column(nullable = false)
    private String cellNumber;

    private String phoneNumber;

    private String address;

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

        if (id != entry.id) return false;
        if (!lastName.equals(entry.lastName)) return false;
        if (!firstName.equals(entry.firstName)) return false;
        if (!patronymic.equals(entry.patronymic)) return false;
        if (!cellNumber.equals(entry.cellNumber)) return false;
        if (!phoneNumber.equals(entry.phoneNumber)) return false;
        if (!address.equals(entry.address)) return false;
        if (!email.equals(entry.email)) return false;
        return owner.equals(entry.owner);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + patronymic.hashCode();
        result = 31 * result + cellNumber.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + owner.hashCode();
        return result;
    }
}
