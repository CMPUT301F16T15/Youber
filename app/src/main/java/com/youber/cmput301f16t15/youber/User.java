package com.youber.cmput301f16t15.youber;

/**
 * Created by Reem on 2016-10-13.
 */
public class User {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    public User(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber) {
    }

    public String getFirstName() {
        return null;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
