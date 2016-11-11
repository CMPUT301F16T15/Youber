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
    /**
     * The Start location.
     */
    GeoLocation startLocation;
    /**
     * The End location.
     */
    GeoLocation endLocation;

    private boolean status = true; //open is true
    private String keyword;
    private Payment payment;
    private int confirmationStage = 0; //0 initial, 1 accepted by a driver, 2 confirmed by a rider, 3 finalized by driver
    private boolean accepted = false;
    private Driver driver;

    public Driver getDriver() {
        return driver;
    }

    /**
     * The enum Request status.
     * Opened request status
     * Accepted by driver(s) status
     * Confirmed by rider status
     * Finalized by driver status
     * Payed/Completed
     */
    public enum RequestStatus {
        opened, acceptedByDrivers, confirmedByRider, finalizedByDriver, payed
    }

    private RequestStatus currentStatus;

    /**
     * Instantiates a new Request.
     *
     * @param location1 the location 1
     * @param location2 the location 2
     */
    public Request(GeoLocation location1, GeoLocation location2) {
        if (location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());

        startLocation = location1;
        endLocation = location2;

        uuID = UUID.randomUUID();
        currentStatus = RequestStatus.opened;
    }

    /**
     * Instantiates a new Request.
     *
     * @param geoLocation1 the geo location 1
     * @param geoLocation2 the geo location 2
     * @param s            the string of keywords
     */
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

    public Rider addRider(Rider rider) {
        return null;
    }

    public UUID getUUID() {
        return this.uuID;
    }

    /**
     * Gets start location.
     * @return the start location
     */
    public GeoLocation getStartLocation() {
        return startLocation;
    }

    /**
     * Gets end location.
     * @return the end location
     */
    public GeoLocation getEndLocation() {
        return endLocation;
    }

    /**
     * Close.
     */
    public void close() {
    }

    /**
     * Is closed boolean.
     *
     * @return the boolean
     */
    public boolean isClosed() {
        if (status) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Accept.
     */
    public void accept() {
        this.accepted = true;
    }

    /**
     * Is accepted boolean.
     * @return the boolean
     */
    public boolean isAccepted() {
        return this.accepted;
    }

    /**
     * Gets distance in Kilometers.
     * @return the distance in kilometers
     */
    public Double getDistance() {
        float distanceFloat[] = new float[1];

        // this returns the approximate amount in meters
        android.location.Location.distanceBetween(getStartLocation().getLat(), getStartLocation().getLon(),
                getEndLocation().getLat(), getEndLocation().getLon(), distanceFloat);

        Double distance = (double)(distanceFloat[0]/1000);
        return distance;
    }

    /**
     * Gets estimated fare.
     * @return the fare
     */
    public Double getFare() {
        Double estFare = 0.00;
        Double dist = getDistance();

        if(dist > 5000) {
            estFare += (0.54)*5000;
            dist = dist - 5000;
            estFare += (0.48)*dist;
        }
        else
            estFare += (0.54)*dist;

        return estFare;
    }

    /**
     * Complete.
     */
    public void complete() {
    }

    /**
     * Is complete boolean.
     * @return the boolean
     */
    public boolean isComplete() {
        return false;
    }

    /**
     * Gets cost settles upon by the rider and driver.
     * @return the cost
     */
    public Double getCost() {
        return this.payment.getActualCost();
    }

    /**
     * Add driver driver.
     *
     * @param driver the driver
     * @return the driver
     */
    public Driver addDriver(Driver driver) {
        return null;
    }

    /**
     * Confirm by driver.
     * @param driver the driver
     */
    public void confirmByDriver(Driver driver) {
        this.confirmationStage = 1;
        currentStatus = RequestStatus.acceptedByDrivers;
    }

    /**
     * Finalize by driver.
     */
    public void finalizeByDriver() {
        this.confirmationStage = 3;
        currentStatus = RequestStatus.finalizedByDriver;
    }

    /**
     * Is confirmed boolean.
     *
     * @return the boolean
     */
    public boolean isConfirmed() {
        return false;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return null;
    }

    public RequestStatus getCurrentStatus() {
        return currentStatus;
    }

}
