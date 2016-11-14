package com.youber.cmput301f16t15.youber.users;

import android.util.Log;

import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 *
 * <p>
 *     Subclass of User and one of the two types of users that Youber has.
 *     A rider creates a request for a driver and confirms it. He will also pay the driver when
 *     the request is complete
 * </p>
 *
 * @see User
 * @see com.youber.cmput301f16t15.youber.users.User.UserType
 */
public class Rider extends User {

    private RequestCollection requests = new RequestCollection();

    /**
     * Instantiates a new Rider.
     */
// default constructor
    public Rider() {
        super();
    }

    /**
     * Instantiates a new Rider.
     *
     * @param username    the username
     * @param firstName   the first name
     * @param lastName    the last name
     * @param dateOfBirth the date of birth
     * @param phoneNumber the phone number
     * @param email       the email
     * @param userType    the user type
     */
// contructor for general user class
    public Rider(String username, String firstName, String lastName, String dateOfBirth, String phoneNumber, String email, UserType userType) {
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

    /**
     * Gets request based on unique identifier.
     *
     * @param uuid the uuid
     * @return the request
     */
    public Request getRequest(UUID uuid) {
        RequestCollection allRequests = requests.getRequestsForRiders(this);
        return requests.get(uuid);
    }

    /**
     * Gets open requests.
     *
     * @return the open requests
     */
    public RequestCollection getOpenRequests() {
        return requests.getRequestsForRiders(this);
    }

    /**
     * Gets closed requests.
     *
     * @return the closed requests
     */
// TODO double check all of these requests bc the naming is so confusing
    public RequestCollection getClosedRequests() {
        RequestCollection riderRequests = requests.getRequestsForRiders(this);
        return riderRequests.getFinalizedRequestToDriver();
    }

//    public RequestCollection getRequestUUIDs() {
//        return requests.getRequestsForRiders(this);
//    }

    /**
     * Gets status.
     *
     * @param uuid the unique identifier
     * @return the status
     */
    public String getStatus(UUID uuid) {
        RequestCollection allRequests = requests.getRequestsForRiders(this);
        Request request = allRequests.get(uuid);
        return request.getDescription();
    }

    /**
     * Call a driver using their contact info.
     *
     * @param driver the driver
     * @return the boolean
     */
    public boolean call(Driver driver) {
        return false;
    }

    /**
     * Email a driver using their contact info.
     *
     * @param driver the driver
     * @return the boolean
     */
    public boolean email(Driver driver) {
        return false;
    }

    /**
     * Make payment.
     *
     * @param uuid the uuid
     */
    public void makePayment(UUID uuid) {
        RequestCollection allRequests = requests.getRequestsForRiders(this);
        Request request =  allRequests.get(uuid);
//        request.getCost();
    }

    public void addRequest(Request request1) {
        requests.add(request1);
    }

    /**
     * Delete request.
     *
     * @param request the request
     */
    public void deleteRequest(Request request) {
        requests.remove(request.getUUID());
    }
}
