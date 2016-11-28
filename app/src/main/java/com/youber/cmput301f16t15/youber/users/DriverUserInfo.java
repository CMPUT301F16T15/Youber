package com.youber.cmput301f16t15.youber.users;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Reem on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 *
 * This class contains the information for a driver (vehicle info)
 * @see UserController
 */

public class DriverUserInfo {

    private String make;
    private String model;
    private String year;
    private String color;

    private HashSet<UUID> acceptedRequests;
    private HashSet<UUID> confirmedRequests;

    public DriverUserInfo()
    {
        this.make = "";
        this.model = "";
        this.year = "";
        this.color = "";

        acceptedRequests = new HashSet<UUID>();
        confirmedRequests = new HashSet<UUID>();
    }

    public DriverUserInfo(String make, String model, String year, String color)
    {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;

        acceptedRequests = new HashSet<UUID>();
        confirmedRequests = new HashSet<UUID>();
    }


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HashSet<UUID> getAcceptedRequests() {
        return acceptedRequests;
    }

    public void setAcceptedRequests(HashSet<UUID> acceptedRequests) {
        this.acceptedRequests = acceptedRequests;
    }

    public HashSet<UUID> getConfirmedRequests() {
        return confirmedRequests;
    }

    public void setConfirmedRequests(HashSet<UUID> confirmedRequests) {
        this.confirmedRequests = confirmedRequests;
    }
}
