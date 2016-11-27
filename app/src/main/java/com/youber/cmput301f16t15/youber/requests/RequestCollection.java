package com.youber.cmput301f16t15.youber.requests;



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


    /**
     * Instantiates a new Request collection.
     */
    public RequestCollection()
    {
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

    public RequestCollection filterByPricePerKm(Double minPricePerKm,Double maxPricePerKm){
        RequestCollection filteredRequestCollection=new RequestCollection();
        for (Request request:this.values()) {
            Double pricePerKm=request.getCost()/request.getDistance();
            if(pricePerKm>minPricePerKm &&pricePerKm<maxPricePerKm){
                filteredRequestCollection.put(request.getUUID(),request);
            }
        }
        return filteredRequestCollection;
    }
    public RequestCollection filterByPrice(Double minPrice,Double maxPrice){
        RequestCollection filteredRequestCollection=new RequestCollection();
        for (Request request:this.values()) {

            if(request.getCost()>minPrice &&request.getCost()<maxPrice){
                filteredRequestCollection.put(request.getUUID(),request);
            }
        }
        return filteredRequestCollection;
    }
}
