package com.youber.cmput301f16t15.youber.users;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 *
 * <p>
 *     Subclass of User and one of the two types of users that Youber has.
 *     A driver gets a request from a rider and accepts it, much like a taxi driver would.
 * </p>
 *
 * @see User
 * @see com.youber.cmput301f16t15.youber.users.User.UserType
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
     * Gets request from rider via a unique ID
     *
     * @param uuid the uuid
     * @return the request
     *
     * @see UUID
     */
    public Request getRequest(UUID uuid) {
        return requestsCtrl.getRequestByUUID(uuid);
    }

    /**
     * Gets status of a request
     *
     * @param uuid the uuid
     * @return the status
     *
     * @see com.youber.cmput301f16t15.youber.requests.Request.RequestStatus
     */
    public boolean getStatus(UUID uuid) {
        return requestsCtrl.getRequestStatus(uuid);
    }

    /**
     * Gets offer payment from request.
     *
     * @param uuid the uuid
     * @return the offer payment from request
     *
     * @see com.youber.cmput301f16t15.youber.misc.Payment
     */
    public Double getOfferPaymentFromRequest(UUID uuid)  //see how much is being offered
    {
        Request request = requestsCtrl.getRequestByUUID(uuid);
        return request.getCost();
    }

    /**
     * Gets a collection of already accepted requests.
     *
     * @return the accepted requests
     *
     * @see RequestCollection
     */
    public RequestCollection getAcceptedRequests() {
        RequestCollection requests = requestsCtrl.getFinalizedRequestToDriver();
        return requests;
    }

    /**
     * Gets a collection of pending requests.
     *
     * @return the pending requests
     *
     * @see RequestCollection
     */
    public RequestCollection getPendingRequests() {
        RequestCollection pendingRequests = requestsCtrl.getPendingRequestsForDrivers(this);
        return pendingRequests;
    }

    /**
     * Gets a single pending request based on its uuid.
     *
     * @param uuid the unique identifier of a request
     * @return the pending request
     */
    public Request getPendingRequest(UUID uuid) {
        RequestCollection pendingRequestSet = requestsCtrl.getPendingRequestsForDrivers(this);
        Request request = pendingRequestSet.getRequestByUUID(uuid);
        return request;
    }

    /**
     * Confirm a single request.
     *
     * @param request1 a specific request
     *
     * @see #getPendingRequest(UUID)
     */
    public void confirm(Request request1) {
        request1.confirmByDriver(this);
    }

    /**
     * Finalize a single request.
     *
     * @param request finalizing an accepted request
     *
     * @see #confirm(Request)
     */
//    public void finalize(Request request) {
//        request.finalizeByDriver();
//    }

    /**
     * Delete a single request.
     *
     * @param request the request to delete
     */
    public void deleteRequest(Request request)
    {
        requestsCtrl.remove(request.getUUID());
    }

    /**
     * Search request by geo location and return a request collection matching the parameters.
     *
     * @param location the location
     * @param radius   the radius
     * @return the request collection
     *
     * @see GeoLocation
     */
    public RequestCollection searchRequestByGeoLocation(GeoLocation location, double radius) {
        return null;
    }

}
