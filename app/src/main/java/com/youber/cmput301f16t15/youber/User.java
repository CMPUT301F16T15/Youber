package com.youber.cmput301f16t15.youber;

import java.util.Observable;

import io.searchbox.annotations.JestId;

/**
 * Created by Reem on 2016-10-13.
 */
public class User extends Observable {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;

    @JestId
    private String username;

//    public static User getUser() {
//        static ;
//    }

    public User()
    {

    }

    public User(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email) {
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.phoneNumber=phoneNumber;
        this.email=email;

    }

    public String getUsername()
    {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setChanged();
        notifyObservers();
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        setChanged();
        notifyObservers();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        setChanged();
        notifyObservers();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setChanged();
        notifyObservers();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setChanged();
        notifyObservers();
    }
}
