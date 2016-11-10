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

    private boolean status = true; //open is true
    private Location location;
    private String keyword;
    private Payment payment;
    private int confirmationStage = 0; //0 initial, 1 accepted by a driver, 2 confirmed by a rider, 3 finalized by driver
    private Rider rider;
    private Driver driver;
    private boolean accepted = false;

    public enum RequestStatus {
        opened, acceptedByDrivers, confirmedByRider, finalizedByDriver, payed
    }

    private RequestStatus currentStatus;

    public Request(GeoLocation location1, GeoLocation location2) {
        if (location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());

        startLocation = location1;
        endLocation = location2;

        uuID = UUID.randomUUID();
        currentStatus = RequestStatus.opened;
    }

    public Request(GeoLocation location1, GeoLocation location2, GeoLocation currLocation, Payment payment)
    {
        if(location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());
        this.location = new Location(location1, location2, currLocation);
    }

    public Request(){
        uuID = UUID.randomUUID();
    };

    public Request(GeoLocation geoLocation1, GeoLocation geoLocation2, String s) {
        uuID = UUID.randomUUID();
    }

    @Override
    public String toString() {
        String currentStat = currentStatus.toString(); // TODO FIX THIS
        String start = startLocation.toString();
        String end = endLocation.toString();
        return currentStat + "\n" + start + "\n" + end;
    }

    public Request(GeoLocation geoLocation1, GeoLocation geoLocation2, GeoLocation currLocation, String s, Payment payment) {
        this.location = new Location(geoLocation1, geoLocation2, currLocation );
        this.keyword = s;
        this.payment = payment;
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
        return startLocation;
    }

    public GeoLocation getEndLocation() {
        return endLocation;
    }

    public void close() {
    }

    public boolean isClosed() {
        if (status) {
            return false;
        }
        else {
            return true;
        }
    }

    public void accept() {
        this.accepted = true;
    }

    public boolean accept(Driver driver) {
        return false;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public void cancel() {
    }

    public Driver getDriver() {
        return this.driver;
    }

    public Double getDistance() {
        return null;
    }

    public Double getFare() {
        return this.payment.getActualCost();
    }

    public void complete() {
    }

    public boolean isComplete() {
        return false;
    }

    public Double getCost() {
        return this.payment.getActualCost();
    }

    public Driver addDriver(Driver driver) {
        return null;
    }

    public boolean contains(RequestCollection uuid) {
        return false;
    }

    public void confirmByDriver(Driver driver) {
        this.confirmationStage = 1;
        currentStatus = RequestStatus.acceptedByDrivers;
    }

    public void finalizeByDriver() {
        this.confirmationStage = 3;
        currentStatus = RequestStatus.finalizedByDriver;
    }

    public boolean isConfirmed() {
        return false;
    }

    public String getDescription() {
        return null;
    }

}
