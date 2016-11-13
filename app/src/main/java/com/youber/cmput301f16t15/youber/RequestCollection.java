package com.youber.cmput301f16t15.youber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class RequestCollection extends HashMap<UUID,Request> implements Serializable{

//    private HashMap<UUID, Request> requestCollection;

    /**
     * Instantiates a new Request collection.
     */
    public RequestCollection()
    {
//        this = new HashMap<UUID, Request>();
    }

    /**
     * Driver accept.
     */
    public void driverAccept() {

    }

    /**
     * Get request by uuid request.
     *
     * @param uuid the uuid
     * @return the request
     */
    public Request getRequestByUUID(UUID uuid){
        return this.get(uuid);
    }

    /**
     * Gets request status.
     *
     * @param uuid the uuid
     * @return the request status
     */
    public boolean getRequestStatus(UUID uuid) {
        return this.get(uuid).isClosed();
    }

    /**
     * Gets by geolocation.
     *
     * @param geoLocation the geo location
     * @param radius      the radius
     * @return the by geolocation
     */
    public RequestCollection getByGeolocation(GeoLocation geoLocation, double radius) {
        RequestCollection requestCollectionSearchedByGeoLocation = new RequestCollection();
        // find by geolocation
        return requestCollectionSearchedByGeoLocation;
    }

    /**
     * Gets by keyword.
     *
     * @param s the s
     * @return the by keyword
     */
    public RequestCollection getByKeyword(String s) {
        return null;
    }

    /**
     * Contains boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public boolean contains(Request request) {
        return false;
    }

    /**
     * Gets finalized request to driver.
     *
     * @return the finalized request to driver
     */
    public RequestCollection getFinalizedRequestToDriver() {
        return null;
    }

    /**
     * Gets requests for riders.
     *
     * @param currentUser the current user
     * @return the requests for riders
     */
// returning every single requests..
    public RequestCollection getRequestsForRiders(Rider currentUser) {
        return null;
    }


    /**
     * Gets pending requests for drivers.
     *
     * @param currentUser the current user
     * @return the pending requests for drivers
     */
    public RequestCollection getPendingRequestsForDrivers(Driver currentUser) {
        return null;
    }

    /**
     * Add.
     *
     * @param request1 the request 1
     */
    public void add(Request request1) {
        this.put(request1.getUUID(), request1);
    }

    /**
     * Add all.
     *
     * @param requests the requests
     */
// add a bulk amount of requests, typically will be called when grabbing from elasticSearch
    public void addAll(List<Request> requests) {
        for(Request r : requests)
        {
            this.put(r.getUUID(), r);
        }
    }
}
