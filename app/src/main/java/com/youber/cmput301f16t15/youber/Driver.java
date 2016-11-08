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

    public Double getOfferPaymentFromRequest(UUID uuid)  //see how much is being offered
    {
        Request request = requestsCtrl.getRequestByUUID(uuid);
        double offer = request.getFare(); //can be getCost instead
        return offer;
    }

    public RequestCollection getAcceptedRequests() {
        RequestCollection requests = requestsCtrl.getFinalizedRequestToDriver();
        return requests;
    }

    public RequestCollection getPendingRequests() {
        RequestCollection pendingRequests = requestsCtrl.getPendingRequestsForDrivers(this);
        return pendingRequests;
    }

    public Request getPendingRequest(UUID uuid) {
        RequestCollection pendingRequestSet = requestsCtrl.getPendingRequestsForDrivers(this);
        Request request = pendingRequestSet.getRequestByUUID(uuid);
        return request;
    }

    public void confirm(Request request1) {
        request1.confirmByDriver(this);
    }

    public void finalize(Request request) {
        request.finalizeByDriver();
    }

    public void deleteRequest(Request request)
    {
        requestsCtrl.remove(request.getUUID());
    }

    public RequestCollection searchRequestByGeoLocation(GeoLocation location, double radius) {
        return null;
    }

}
