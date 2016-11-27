package com.youber.cmput301f16t15.youber.requests;

import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.users.Rider;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 * <p>
 *     This class is used to store multiple requests in a specific collection.
 * </p>
 * @see Request
 * @see RequestCollectionsController
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
     * Get request by uuid request.
     *
     * @param uuid the uuid
     * @return the request
     */
    public Request getRequestByUUID(UUID uuid){
        return this.get(uuid);
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

    @Override
    public int hashCode(){
        HashCodeBuilder hashCodeBuilder= new HashCodeBuilder();
        for(Request request: this.values()){
            hashCodeBuilder.append(request);
        }
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public  boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof RequestCollection))return false;
        RequestCollection otherRequestCollection=(RequestCollection)other;
        if(this.size()!=otherRequestCollection.size())return false;
        EqualsBuilder equalsBuilder= new EqualsBuilder();

        for(UUID uuid: this.keySet()){

            if(!otherRequestCollection.containsKey(uuid))return false;

            if(!this.getRequestByUUID(uuid).equals(otherRequestCollection.getRequestByUUID(uuid))) return false;

        }


        return true;
    }

}
