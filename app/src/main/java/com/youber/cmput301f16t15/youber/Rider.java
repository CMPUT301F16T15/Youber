package com.youber.cmput301f16t15.youber;

import android.util.Log;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class Rider extends User {

    private RequestCollection requests = new RequestCollection();

    // default constructor
    public Rider() {
        super();
    }

    // contructor for general user class
    public Rider(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email) {
        super(username, firstName, lastName, dateOfBirth, phoneNumber, email);

        // do we create a new request collection here or grab from elastic search.. (what about offline)
        ElasticSearchRequest.getObjects getElasticRequest = new ElasticSearchRequest.getObjects();
        getElasticRequest.execute("");

        try {
            requests.addAll(getElasticRequest.get());
        }
        catch (Exception e) {
            Log.i("Error", "The call to get requests from elastic search failed: " + e.toString());
        }
    }

    public Request getRequest(UUID uuid) {
        RequestCollection allRequests = requests.getRequestsForRiders(this);
        return requests.get(uuid);
    }

    public RequestCollection getOpenRequests() {
        return requests.getRequestsForRiders(this);
    }

    // TODO double check all of these requests bc the naming is so confusing
    public RequestCollection getClosedRequests() {
        RequestCollection riderRequests = requests.getRequestsForRiders(this);
        return riderRequests.getFinalizedRequestToDriver();
    }

    public RequestCollection getRequests() {
        return requests.getRequestsForRiders(this);
    }

    public String getStatus(UUID uuid) {
        RequestCollection allRequests = requests.getRequestsForRiders(this);
        Request request = allRequests.get(uuid);
        return request.getDescription();
    }

    public boolean call(Driver driver) {
        return false;
    }

    public boolean email(Driver driver) {
        return false;
    }

    public void makePayment(UUID uuid) {
        RequestCollection allRequests = requests.getRequestsForRiders(this);
        Request request =  allRequests.get(uuid);
//        request.getCost();
    }

    public void addNewRequest(GeoLocation geoLocation1, GeoLocation geoLocation2, GeoLocation currLocation, Payment payment) {
        // make a request object
        Request request = new Request(geoLocation1, geoLocation2, currLocation, payment);
        requests.add(request);
    }

// should not be in this layer of Rider, you can add by first getting the collection and then adding (HOPEFULLY not needig to be static)
//
//    public void addNewRequest(GeoLocation geoLocation1, GeoLocation geoLocation2) {
//        // make a request object
//        Request request = new Request(geoLocation1, geoLocation2);
//        requests.add(request);
//    }
//
    public void addRequest(Request request1) {
        requests.add(request1);
    }

    public void deleteRequest(Request request) {
        requests.remove(request.getUUID());
    }
}
