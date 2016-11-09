package com.youber.cmput301f16t15.youber;

import android.widget.ArrayAdapter;

import org.apache.http.protocol.RequestUserAgentHC4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.UUID;
import io.searchbox.annotations.JestId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reem on 2016-10-13.
 */
public class User extends Observable implements Serializable {

    @JestId
    private String username;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;

    public enum UserType {
        rider, driver
    }

    private UserType currentUserType; // to indicate whether the user is currently a rider or a driver

    RequestCollection riderRequests; // hold both rider and driver requests
    RequestCollection driverRequests; // depending on their userType

    // is this suppose to be a list of uuids?
//    ArrayList<UUID> riderRequestUUIDs = new ArrayList<UUID>();
//    ArrayList<UUID> driverRequestUUIDs = new ArrayList<UUID>();

    public User()
    {

    }

    public User(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email, UserType userType) {
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.currentUserType = userType;

        riderRequests = new RequestCollection();
        driverRequests = new RequestCollection();
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

//    public ArrayList<UUID> getRequestUUIDs() { // note this returns the relavant uuids respective to the current user type
//        if(currentUserType == UserType.rider)
//            return riderRequestUUIDs;
//
//        return driverRequestUUIDs;
//    }

    public RequestCollection getRequests() {
        if(currentUserType == UserType.rider)
            return riderRequests;

        return driverRequests;
    }

    public void add(Request r) {
        if(currentUserType == UserType.rider)
            riderRequests.add(r);

        driverRequests.add(r);
        int i = 0;
    }
}
