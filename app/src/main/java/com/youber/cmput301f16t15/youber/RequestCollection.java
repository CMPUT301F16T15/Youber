package com.youber.cmput301f16t15.youber;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class RequestCollection extends HashMap<UUID,Request>{

    private HashMap<UUID, Request> requestCollection;

    public RequestCollection()
    {
        this.requestCollection = new HashMap<UUID, Request>();
    }

    public void driverAccept() {

    }

    public Request getRequestByUUID(UUID uuid){
        return requestCollection.get(uuid);
    }

    public boolean getRequestStatus(UUID uuid) {
        return requestCollection.get(uuid).isClosed();
    }

    public RequestCollection getByGeolocation(GeoLocation geoLocation, double radius) {
        RequestCollection requestCollectionSearchedByGeoLocation = new RequestCollection();
        // find by geolocation
        return requestCollectionSearchedByGeoLocation;
    }

    public RequestCollection getByKeyword(String s) {
        return null;
    }

    public boolean contains(Request request) {
        return false;
    }

    public RequestCollection getFinalizedRequestToDriver() {
        return null;
    }

    public RequestCollection getRequestsForRiders(Rider currentUser) {
        return null;
    }

    public RequestCollection getPendingRequestsForDrivers(Driver currentUser) {
        return null;
    }

    public void add(Request request1) {

    }
}
