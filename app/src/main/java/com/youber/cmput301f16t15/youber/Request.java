package com.youber.cmput301f16t15.youber;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */

public class Request {

    private UUID uuID;

    private boolean status = true; //open is true
    private Location location;
    private Collection<String> keyword;
    private Payment payment;
    private int confirmationStage = 0; //0 initial, 1 accepted by a driver, 2 confirmed by a rider, 3 finalized by driver
    private Rider rider;
    private Driver driver;
    private boolean accepted = false;

    public Request(GeoLocation location1, GeoLocation location2, GeoLocation currLocation, Payment payment)
    {
        if(location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());
        this.location = new Location(location1, location2, currLocation);
    }

    public Request(){};

    public Request(GeoLocation geoLocation1, GeoLocation geoLocation2, GeoLocation currLocation, Collection<String> s, Payment payment) {
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
        return this.location.getStartLocation();
    }

    public GeoLocation getEndLocation() {
        return this.location.getEndLocation();
    }

    public void close() {
        this.status = false;
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

    public Rider getRider() {
        return this.rider;
    }

    public Double getDistance() {
        return null;
    }

    public Double getFare() {
        return this.payment.getActualCost();
    }

    public void complete() {
        this.status = false;
    }

    public boolean isComplete() {
        return false;
    }

    public Double getCost() {
        return this.payment.getActualCost();
    }

    public void addDriver(Driver driver) {
        this.driver = driver;
    }

    public boolean contains(RequestCollection uuid) {
        return false;
    }

    public void confirmByDriver(Driver driver) {
        this.confirmationStage = 1;
    }

    public void finalizeByDriver() {
        this.confirmationStage = 3;
    }

    public boolean isConfirmed() {
        return false;
    }

    public String getDescription() {
        return null;
    }

    public Collection<String> getKeywords() {
        return this.keyword;
    }

    public int getConfirmationStage() {
        return this.confirmationStage;
    }

}
