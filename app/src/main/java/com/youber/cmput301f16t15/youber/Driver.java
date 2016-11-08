package com.youber.cmput301f16t15.youber;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class Driver extends User {

    private RequestCollection requestsCtrl;

    public Driver() {
        super();
    }

    public Driver(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email) {
        super(username, firstName, lastName, dateOfBirth, phoneNumber, email);
    }

    public Request getRequest(UUID uuid) {
        return requestsCtrl.getRequestByUUID(uuid);
    }

    public boolean getStatus(UUID uuid) {
        return requestsCtrl.getRequestStatus(uuid);
    }

    public Double getOfferPayment(UUID uuid) {
        return null;
    }

    public RequestCollection getAcceptedRequests() {
        RequestCollection requests = requestsCtrl.getAcceptedRequestsForDrivers(this);
        return requests;
    }

    public RequestCollection getPendingRequests() {
        return null;
    }

    public Request getPendingRequest(UUID uuid) {
        return null;
    }

    public void confirm(Request request1) {
    }
}
