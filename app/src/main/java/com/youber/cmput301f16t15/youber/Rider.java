package com.youber.cmput301f16t15.youber;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class Rider extends User {

    private RequestCollection requestsCtrl;

    // default constructor
    public Rider() {
        super();
    }

    // contructor for general user class
    public Rider(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email) {
        super(username, firstName, lastName, dateOfBirth, phoneNumber, email);
    }


    public Request getRequest(UUID uuid) {
        RequestCollection requests = requestsCtrl.getRequestsForRiders(this);
        return requests.get(uuid);
    }

    public RequestCollection getOpenRequests() {
        return requestsCtrl.getRequestsForRiders(this);
    }

    public RequestCollection getClosedRequests() {
        RequestCollection requests = requestsCtrl.getRequestsForRiders(this);
        return requests.getFinalizedRequestToDriver();
    }

    public RequestCollection getRequests() {
        return requestsCtrl.getRequestsForRiders(this);
    }

    public String getStatus(UUID uuid) {
        RequestCollection requests = requestsCtrl.getRequestsForRiders(this);
        Request request = requests.get(uuid);
        return request.getDescription();
    }

    public boolean call(Driver driver) {
        return false;
    }

    public boolean email(Driver driver) {
        return false;
    }

    public void makePayment(UUID uuid) {
        RequestCollection acceptedRequests = requestsCtrl.getRequestsForRiders(this);
        Request request =  acceptedRequests.get(uuid);
//        request.getCost();
    }

    public void addNewRequest(GeoLocation geoLocation1, GeoLocation geoLocation2, GeoLocation currLocation, Payment payment) {
        // make a request object
        Request request = new Request(geoLocation1, geoLocation2, currLocation, payment);
        requestsCtrl.add(request);
    }

    public void addRequest(Request request1) {
        requestsCtrl.add(request1);
    }

    public void deleteRequest(Request request) {
        requestsCtrl.remove(request.getUUID());
    }
}
