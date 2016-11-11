package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests;

/**
 * Created by Calvi on 2016-11-07.
 */
public class Location {
    private GeoLocation startLocation;
    private GeoLocation endLocation;
    private GeoLocation currentLocation;
    private double rideDistance;

    /**
     * Instantiates a new Location.
     *
     * @param startLocation   the start location
     * @param endLocation     the end location
     * @param currentLocation the current location
     */
    public Location(GeoLocation startLocation, GeoLocation endLocation, GeoLocation currentLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.currentLocation = currentLocation;
    }

    /**
     * Find ride distance.
     */
    public void findRideDistance() {
        //do something to find distance
        this.rideDistance = 0;
    }
}
