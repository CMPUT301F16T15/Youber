package com.youber.cmput301f16t15.youber.requests;

import com.youber.cmput301f16t15.youber.gui.RiderViewRequestActivity;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.exceptions.InvalidRequestException;
import com.youber.cmput301f16t15.youber.misc.Payment;



import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import java.io.Serializable;
import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * Created by Reem on 2016-10-13.
 *
 * <p>
 *     The request class handles new or preexisting requests from a user.
 * </p>
 *
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see RiderViewRequestActivity
 * @see RequestCollection
 */
public class Request implements Serializable {

    @JestId
    private UUID uuID;
    double [] startLocation =new double[2];
    String startLocStr = "";
    double [] endLocation= new double[2];
    String endLocStr = "";

    private String description = "";
    private Double distance = 0.0;
    private Payment payment = new Payment(0);

    public String getDriverUsernameID() {
        return driverUsernameID;
    }

    public void setDriverUsernameID(String driverUsernameID) {
        this.driverUsernameID = driverUsernameID;
    }

    private String driverUsernameID = ""; // only to be filled in when this is the confirmed driver

    /**
     * The enum Request status.
     * Opened request status
     * Accepted by driver(s) status
     * Confirmed by rider status
     * Finalized by driver status
     * Payed/Completed
     */
    public enum RequestStatus {
        opened,acceptedByDrivers, riderSelectedDriver, paid, completed
    }

    private RequestStatus currentStatus;

    /**
     * Instantiates a new Request.
     *
     * @param location1 the starting location
     * @param location2 the end location
     */
    public Request(GeoLocation location1, String start, GeoLocation location2, String end) {
        if (location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());

        startLocation[0] = location1.getLat();
        startLocation[1] = location1.getLon();
        endLocation[0] = location2.getLat();
        endLocation[1] = location2.getLon();

        uuID = UUID.randomUUID();
        currentStatus = RequestStatus.opened;

        startLocStr = start;
        endLocStr = end;
    }

    @Override
    public String toString() {
        String currentStat = currentStatus.toString(); // TODO FIX THIS
        return currentStat + "\nStart: " + startLocStr + "\nEnd: " + endLocStr;
    }

    public UUID getUUID() {
        return this.uuID;
    }

    /**
     * Gets start location.
     * @return the start location
     */
    public GeoLocation getStartLocation() {
        return new GeoLocation(startLocation[0],startLocation[1]);
    }

    public String getStartLocStr() {
        return startLocStr;
    }

    /**
     * Gets end location.
     * @return the end location
     */
    public GeoLocation getEndLocation() {

        return new GeoLocation(endLocation[0],endLocation[1]);
    }

    public String getEndLocStr() {
        return endLocStr;
    }

    /**
     * Gets cost settles upon by the rider and driver.
     * @return the cost
     */
    public Double getCost() {
        return this.payment.getActualCost();
    }

    public void setPayment(double payAmt) {
        this.payment = new Payment(payAmt);
    }


    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String s) {
        description = s;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double d) {
        this.distance = d;
    }


    public RequestStatus getCurrentStatus() {
        return currentStatus;
    }


    //opened, acceptedByDrivers, riderSelectedDriver, paid
    public void setCompleted(){
        this.currentStatus=RequestStatus.completed;
    }
    public void setAcceptedByDrivers(){
        this.currentStatus=RequestStatus.acceptedByDrivers;
    }

    public void setRiderSelectedDriver(){
        this.currentStatus=RequestStatus.riderSelectedDriver;
    }

    public void setPaid(){
        this.currentStatus=RequestStatus.paid;
    }

    //http://stackoverflow.com/questions/185937/overriding-the-java-equals-method-quirk
    //http://stackoverflow.com/questions/10912646/hashcodebuilder-and-equalsbuilder-usage-style

    @Override
    public int hashCode(){
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(getUUID());
        hashCodeBuilder.append(getCurrentStatus());
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Request))return false;
        Request otherRequest=(Request)other;
        EqualsBuilder equalsBuilder= new EqualsBuilder();
        equalsBuilder.append(getUUID(),otherRequest.getUUID());
        equalsBuilder.append(getCurrentStatus(),otherRequest.getCurrentStatus());
        return equalsBuilder.isEquals();

    }

    // NEVER USE THIS: for testing hash
    public Request(GeoLocation location1, GeoLocation location2, UUID otheruuid) {
        if (location1.equals(location2)) throw new RuntimeException(new InvalidRequestException());

        startLocation[0] = location1.getLat();
        startLocation[1] = location1.getLon();
        endLocation[0] = location2.getLat();
        endLocation[1] = location2.getLon();

        uuID = otheruuid;
        currentStatus = RequestStatus.opened;
    }
}
