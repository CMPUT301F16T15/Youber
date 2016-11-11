package com.youber.cmput301f16t15.youber;

import android.widget.ArrayAdapter;

import org.apache.http.protocol.RequestUserAgentHC4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;
import java.util.UUID;
import io.searchbox.annotations.JestId;
import java.util.ArrayList;
import java.util.List;

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
     // hold both rider and driver requests
    /**
     * The Driver requests.
     */
    private HashSet<UUID> driverUUIDs;
    private HashSet<UUID> riderUUIDs;// depending on their userType

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

        riderUUIDs = new HashSet<UUID>();
        driverUUIDs = new HashSet<UUID>();

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
    public HashSet<UUID> getRequestUUIDs() {
        if(currentUserType == UserType.rider){return riderUUIDs;}

        return driverUUIDs;
    }



    public void addRequesttUUID(UUID uuid){
        if(currentUserType == UserType.rider)
            riderUUIDs.add(uuid);
        else
            driverUUIDs.add(uuid);
    }
    public void removeRequestUUID(UUID uuid){
        if(currentUserType == UserType.rider)
            riderUUIDs.add(uuid);
        else
            driverUUIDs.remove(uuid);
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
