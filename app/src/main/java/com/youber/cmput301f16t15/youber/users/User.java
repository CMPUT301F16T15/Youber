package com.youber.cmput301f16t15.youber.users;


import java.io.Serializable;
import java.util.HashSet;
import java.util.UUID;
import io.searchbox.annotations.JestId;

/**
 * Created by Reem on 2016-10-13.
 * <p>
 *     A user is any person using the app that could be either a rider or a driver or both.
 *     Every user has a unique username and their contact information must be stored for contact
 *     purposes via phone call or email.
 * </p>
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see com.youber.cmput301f16t15.youber.users.User.UserType
 * @see UserController
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


    private RiderUserInfo riderUserInfo;
    private DriverUserInfo driverUserInfo;


    /**
     * Instantiates a new User.
     */
    public User() {

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
     */
    public User(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.currentUserType = UserType.rider;


        riderUserInfo = new RiderUserInfo();
        driverUserInfo = new DriverUserInfo();
    }

    @Override
    public String toString() {
        return username + ", " + firstName + " " + lastName;
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
    }

    public String getMake() { return driverUserInfo.getMake(); }

    public void setMake(String make){ driverUserInfo.setMake(make);}

    public String getModel() { return driverUserInfo.getModel();}

    public void setModel(String model) { driverUserInfo.setModel(model);}

    public String getYear() { return driverUserInfo.getYear();}

    public void setYear(String year) { driverUserInfo.setYear(year);}

    public String getColour() {return driverUserInfo.getColor();}

    public void setColour(String colour){driverUserInfo.setColor(colour);}


    public HashSet<UUID> getRequestUUIDs() {
        if(currentUserType == UserType.rider){return riderUserInfo.getUUIDs();}

        return driverUserInfo.getAcceptedRequests();
    }

    public HashSet<UUID> getRiderUUIDs() {return riderUserInfo.getUUIDs();}

    public HashSet<UUID> getAcceptedDriverUUIDs()
    {
        return driverUserInfo.getAcceptedRequests();
    }

    public HashSet<UUID> getConfirmedDriverUUIDs() {
        return driverUserInfo.getConfirmedRequests();
    }

    public void addToDriverConfirmed(UUID uuid)
    {
        driverUserInfo.getConfirmedRequests().add(uuid);
    }

    public void deleteUUIDFromAccepted(UUID uuid)
    {
        driverUserInfo.getAcceptedRequests().remove(uuid);
    }


    public void addRequestUUID(UUID uuid){
        if(currentUserType == UserType.rider)
            riderUserInfo.getUUIDs().add(uuid);
        else
            driverUserInfo.getAcceptedRequests().add(uuid);
    }

    public void removeRequestUUID(UUID uuid){
        if(currentUserType == UserType.rider)
            riderUserInfo.getUUIDs().remove(uuid);
        else
            driverUserInfo.getAcceptedRequests().remove(uuid);
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
