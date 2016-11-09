package com.youber.cmput301f16t15.youber;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * Created by Reem on 2016-10-13.
 */

public class Request implements Serializable {

    @JestId
    private UUID uuID;
    GeoLocation startLocation;
    GeoLocation endLocation;


    public Request(GeoLocation location1, GeoLocation location2)
    {
        if(location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());

        startLocation = location1;
        endLocation = location2;

        uuID= UUID.randomUUID();
    }

    public Request(){
        uuID = UUID.randomUUID();
    };

    public Request(GeoLocation geoLocation1, GeoLocation geoLocation2, String s) {
        uuID = UUID.randomUUID();
    }

    @Override
    public String toString()
    {
        return uuID.toString();
    }

    public User addRider(User user) {
        return null;
    }

    public Rider addRider(Rider rider) {
        return null;
    }
    public Driver addRider(Driver driver) {
        return null;
    }

    public UUID getUUID() {
        return this.uuID;
    }


    public GeoLocation getStartLocation() {
        return null;
    }

    public GeoLocation getEndLocation() {
        return null;
    }

    public void close() {
    }

    public boolean isClosed() {
        return false;
    }

    public void accept() {
    }

    public boolean accept(Driver driver) {
        return false;
    }

    public boolean isAccepted() {
        return false;
    }

    public void cancel() {
    }

    public Driver getDriver() {
        return null;
    }

    public Double getDistance() {
        return null;
    }

    public Double getFare() {
        return null;
    }

    public void complete() {
    }

    public boolean isComplete() {
        return false;
    }

    public Double getCost() {
        return null;
    }

    public Driver addDriver(Driver driver) {
        return null;
    }

    public boolean contains(RequestCollection uuid) {
        return false;
    }

    public void confirm(Driver driver) {
    }

    public boolean isConfirmed() {
        return false;
    }

    public String getDescription() {
        return null;
    }
}
