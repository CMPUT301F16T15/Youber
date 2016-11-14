package com.youber.cmput301f16t15.youber.users;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class Driver extends User {

    private RequestCollection requestsCtrl;

    /**
     * Instantiates a new Driver.
     */
    public Driver() {
        super();
    }

    /**
     * Instantiates a new Driver.
     *
     * @param username    the username
     * @param firstName   the first name
     * @param lastName    the last name
     * @param dateOfBirth the date of birth
     * @param phoneNumber the phone number
     * @param email       the email
     * @param userType    the user type
     */
    public Driver(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email, UserType userType) {
        super(username, firstName, lastName, dateOfBirth, phoneNumber, email);
    }

    /**
     * Gets request.
     *
     * @param uuid the uuid
     * @return the request
     */
    public Request getRequest(UUID uuid) {
        return requestsCtrl.getRequestByUUID(uuid);
    }

    /**
     * Gets status.
     *
     * @param uuid the uuid
     * @return the status
     */
    public boolean getStatus(UUID uuid) {
        return requestsCtrl.getRequestStatus(uuid);
    }

    /**
     * Gets offer payment from request.
     *
     * @param uuid the uuid
     * @return the offer payment from request
     */
    public Double getOfferPaymentFromRequest(UUID uuid)  //see how much is being offered
    {
        Request request = requestsCtrl.getRequestByUUID(uuid);
        return request.getCost();
    }

    /**
     * Gets accepted requests.
     *
     * @return the accepted requests
     */
    public RequestCollection getAcceptedRequests() {
        RequestCollection requests = requestsCtrl.getFinalizedRequestToDriver();
        return requests;
    }

    /**
     * Gets pending requests.
     *
     * @return the pending requests
     */
    public RequestCollection getPendingRequests() {
        RequestCollection pendingRequests = requestsCtrl.getPendingRequestsForDrivers(this);
        return pendingRequests;
    }

    /**
     * Gets pending request.
     *
     * @param uuid the uuid
     * @return the pending request
     */
    public Request getPendingRequest(UUID uuid) {
        RequestCollection pendingRequestSet = requestsCtrl.getPendingRequestsForDrivers(this);
        Request request = pendingRequestSet.getRequestByUUID(uuid);
        return request;
    }

    /**
     * Confirm.
     *
     * @param request1 the request 1
     */
    public void confirm(Request request1) {
        request1.confirmByDriver(this);
    }

    /**
     * Finalize.
     *
     * @param request the request
     */
//    public void finalize(Request request) {
//        request.finalizeByDriver();
//    }

    /**
     * Delete request.
     *
     * @param request the request
     */
    public void deleteRequest(Request request)
    {
        requestsCtrl.remove(request.getUUID());
    }

    /**
     * Search request by geo location request collection.
     *
     * @param location the location
     * @param radius   the radius
     * @return the request collection
     */
    public RequestCollection searchRequestByGeoLocation(GeoLocation location, double radius) {
        return null;
    }

}
