package com.youber.cmput301f16t15.youber;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class Rider extends User {

    // default constructor
    public Rider() {
        super();
    }

    // contructor for general user class
    public Rider(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email) {
        super(username, firstName, lastName, dateOfBirth, phoneNumber, email);
    }


    public Request getRequest(UUID uuid) {
        return new Request();
    }

    public RequestCollection getOpenRequests() {
        return null;
    }

    public RequestCollection getClosedRequests() {
        return null;
    }

    public RequestCollection getRequests() {
        return null;
    }

    public String getStatus(UUID uuid) {
        return null;
    }

    public boolean call(Driver driver) {
        return false;
    }

    public boolean email(Driver driver) {
        return false;
    }

    public void makePayment(UUID uuid) {

    }

    public void addNewRequest(GeoLocation geoLocation1, GeoLocation geoLocation2) {
        // make a request object

    }

    public void addRequest(Request request1) {
    }
}
