package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.user;

import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.collections.RequestCollection;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

/**
 * Created by Reem on 2016-10-13.
 */
public class User implements Serializable {

    @JestId
    private String username;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;

    /**
     * The enum User type.
     */
    public enum UserType {
        /**
         * Rider user type.
         */
        rider, /**
         * Driver user type.
         */
        driver
    }

    private UserType currentUserType; // to indicate whether the user is currently a rider or a driver

    /**
     * The Rider requests.
     */
    RequestCollection riderRequests; // hold both rider and driver requests
    /**
     * The Driver requests.
     */
    RequestCollection driverRequests; // depending on their userType

    // is this suppose to be a list of uuids?
//    ArrayList<UUID> riderRequestUUIDs = new ArrayList<UUID>();
//    ArrayList<UUID> driverRequestUUIDs = new ArrayList<UUID>();

    /**
     * Instantiates a new User.
     */
    public User()
    {

    }

    /**
     * Instantiates a new User.
     *
     * @param username    the username
     * @param firstName   the first name
     * @param lastName    the last name
     * @param dateOfBirth the date of birth
     * @param phoneNumber the phone number
     * @param email       the email
     * @param userType    the user type
     */
    public User(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email) {
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.currentUserType = UserType.rider;

        riderRequests = new RequestCollection();
        driverRequests = new RequestCollection();

    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;

        //notifyObservers();
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;

        //notifyObservers();
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

        //notifyObservers();
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;

        //notifyObservers();
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;

        //notifyObservers();
    }

//    public ArrayList<UUID> getRequestUUIDs() { // note this returns the relavant uuids respective to the current user type
//        if(currentUserType == UserType.rider)
//            return riderRequestUUIDs;
//
//        return driverRequestUUIDs;
//    }

    /**
     * Gets requests.
     *
     * @return the requests
     */
    public RequestCollection getRequests() {
        if(currentUserType == UserType.rider)
            return riderRequests;

        return driverRequests;
    }

    /**
     * Add.
     *
     * @param r the r
     */
    public void add(Request r) {
        if(currentUserType == UserType.rider)
            riderRequests.add(r);

        driverRequests.add(r);
        int i = 0;
    }


    public UserType getCurrentUserType()
    {
        return currentUserType;
    }

    public void setCurrentUserType(UserType userType)
    {
        currentUserType = userType;
    }

}
