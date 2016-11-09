package com.youber.cmput301f16t15.youber;

import java.util.HashMap;
import java.util.Map;
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


    //http://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-map - ScArcher2
    public RequestCollection getByGeolocation(GeoLocation searchGeoLocation, double radius) {
        RequestCollection requestCollectionSearchedByGeoLocation = new RequestCollection();
        double searchLat = searchGeoLocation.getLat();
        double searchLon = searchGeoLocation.getLon();
        // find by geolocation
        for (Map.Entry<UUID, Request> entry : this.entrySet()) {
            UUID requestUUID = entry.getKey();
            Request request = entry.getValue();
            double startLat = request.getStartLocation().getLat();
            double startLon = request.getStartLocation().getLon();
            if ( (Math.pow(startLat - searchLat, 2) + Math.pow(startLon - searchLon, 2)) <= Math.pow(radius, 2) ) {
                requestCollectionSearchedByGeoLocation.add(requestUUID, request);
            }

        }
        return requestCollectionSearchedByGeoLocation;
    }

    public RequestCollection getByKeyword(String keyword) {
        RequestCollection requestCollectionSearchedByKeyword = new RequestCollection();
        for (Map.Entry<UUID, Request> entry : this.entrySet()) {
            UUID requestUUID = entry.getKey();
            Request request = entry.getValue();
            for (String requestKeyword : request.getKeywords()) {
                //https://www.tutorialspoint.com/java/java_string_equalsignorecase.htm
                if (requestKeyword.equalsIgnoreCase(keyword)){
                    requestCollectionSearchedByKeyword.add(requestUUID, request);
                    break;
                }
            }
        }
        return requestCollectionSearchedByKeyword;
    }

    public boolean contains(Request request) {

        for (Map.Entry<UUID, Request> entry : this.entrySet()) {
            if (entry.getValue().equals(request)){
                return true;
            }
        }
        return false;
    }

    public RequestCollection getFinalizedRequestToDriver() {
        RequestCollection finalizedRequest = new RequestCollection();
        for (Map.Entry<UUID, Request> entry : this.entrySet()) {
            UUID requestUUID = entry.getKey();
            Request request = entry.getValue();
            if (request.getConfirmationStage() == 3) {
                finalizedRequest.add(requestUUID, request);
            }
        }

        return finalizedRequest;
    }

    public RequestCollection getRequestsForRiders(Rider currentUser) {
        RequestCollection riderRequest = new RequestCollection();
        for (Map.Entry<UUID, Request> entry : this.entrySet()) {
            UUID requestUUID = entry.getKey();
            Request request = entry.getValue();
            if (request.getRider().equals(currentUser)) {
                riderRequest.add(requestUUID, request);
            }
        }
        return riderRequest;
    }

    public RequestCollection getPendingRequestsForDrivers(Driver currentUser) {
        RequestCollection pendingRequest = new RequestCollection();
        for (Map.Entry<UUID, Request> entry : currentUser.getRequests().entrySet()) {
            UUID requestUUID = entry.getKey();
            Request request = entry.getValue();
            if (request.getConfirmationStage() == 2) {
                pendingRequest.add(requestUUID, request);
            }
        }
        return pendingRequest;
    }

    public void add(UUID uuid, Request request) {
        requestCollection.put(uuid, request);
    }
}
