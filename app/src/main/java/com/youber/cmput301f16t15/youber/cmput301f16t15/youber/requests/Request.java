package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests;

import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.collections.RequestCollection;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.exceptions.InvalidRequestException;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.user.Driver;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.user.Rider;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.user.User;

import java.io.Serializable;
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
    private Location location;
    private String keyword;
    private Payment payment;
    private int confirmationStage = 0; //0 initial, 1 accepted by a driver, 2 confirmed by a rider, 3 finalized by driver
    private Rider rider;
    private Driver driver;
    private boolean accepted = false;

    /**
     * The enum Request status.
     */
    public enum RequestStatus {
        /**
         * Opened request status.
         */
        opened, /**
         * Accepted by drivers request status.
         */
        acceptedByDrivers, /**
         * Confirmed by rider request status.
         */
        confirmedByRider, /**
         * Finalized by driver request status.
         */
        finalizedByDriver, /**
         * Payed request status.
         */
        payed
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
     * @param location1    the location 1
     * @param location2    the location 2
     * @param currLocation the curr location
     * @param payment      the payment
     */
    public Request(GeoLocation location1, GeoLocation location2, GeoLocation currLocation, Payment payment)
    {
        if(location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());
        this.location = new Location(location1, location2, currLocation);
    }

    /**
     * Instantiates a new Request.
     */
    public Request(){
        uuID = UUID.randomUUID();
    };

    /**
     * Instantiates a new Request.
     *
     * @param geoLocation1 the geo location 1
     * @param geoLocation2 the geo location 2
     * @param s            the s
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

    /**
     * Instantiates a new Request.
     *
     * @param geoLocation1 the geo location 1
     * @param geoLocation2 the geo location 2
     * @param currLocation the curr location
     * @param s            the s
     * @param payment      the payment
     */
    public Request(GeoLocation geoLocation1, GeoLocation geoLocation2, GeoLocation currLocation, String s, Payment payment) {
        this.location = new Location(geoLocation1, geoLocation2, currLocation );
        this.keyword = s;
        this.payment = payment;
    }

    /**
     * Add rider user.
     *
     * @param user the user
     * @return the user
     */
    public User addRider(User user) {
        return null;
    }

    /**
     * Add rider rider.
     *
     * @param rider the rider
     * @return the rider
     */
    public Rider addRider(Rider rider) {
        return null;
    }

    /**
     * Add rider driver.
     *
     * @param driver the driver
     * @return the driver
     */
    public Driver addRider(Driver driver) {
        return null;
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public UUID getUUID() {
        return this.uuID;
    }


    /**
     * Gets start location.
     *
     * @return the start location
     */
    public GeoLocation getStartLocation() {
        return startLocation;
    }

    /**
     * Gets end location.
     *
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
     * Accept boolean.
     *
     * @param driver the driver
     * @return the boolean
     */
    public boolean accept(Driver driver) {
        return false;
    }

    /**
     * Is accepted boolean.
     *
     * @return the boolean
     */
    public boolean isAccepted() {
        return this.accepted;
    }

    /**
     * Cancel.
     */
    public void cancel() {
    }

    /**
     * Gets driver.
     *
     * @return the driver
     */
    public Driver getDriver() {
        return this.driver;
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public Double getDistance() {
        return null;
    }

    /**
     * Gets fare.
     *
     * @return the fare
     */
    public Double getFare() {
        return this.payment.getActualCost();
    }

    /**
     * Complete.
     */
    public void complete() {
    }

    /**
     * Is complete boolean.
     *
     * @return the boolean
     */
    public boolean isComplete() {
        return false;
    }

    /**
     * Gets cost.
     *
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
     * Contains boolean.
     *
     * @param uuid the uuid
     * @return the boolean
     */
    public boolean contains(RequestCollection uuid) {
        return false;
    }

    /**
     * Confirm by driver.
     *
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

}
