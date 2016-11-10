package com.youber.cmput301f16t15.youber;

/**
 * Created by Reem on 2016-10-24.
 */
public class RequestController
{


    /**
     * Add request.
     *
     * @param request the request
     * @param rider1  the rider 1
     */
    public static void addRequest(Request request, Rider rider1)
    {
        rider1.addRequest(request);
    }

    /**
     * Delete request.
     *
     * @param request the request
     * @param rider   the rider
     */
    public static void deleteRequest(Request request, Rider rider)
    {
        if (request.isAccepted()) {
            Driver driver = request.getDriver();
            driver.deleteRequest(request);
        }
        rider.deleteRequest(request);
    }

    /**
     * Confirm request.
     *
     * @param request the request
     * @param driver1 the driver 1
     */
    public static void confirmRequest(Request request, Driver driver1)
    {
        driver1.confirm(request);
    }

    /**
     * Accept request.
     *
     * @param request the request
     */
    public static void acceptRequest(Request request)
    {
        request.accept();
    }

    /**
     * Search request request.
     *
     * @param rider the rider
     * @return the request
     */
    public static Request searchRequest(Rider rider) //********************** "dont worry about it" - Aaron *********************************
    {
        return null;
    }

    /**
     * Link driver with request.
     *
     * @param request1 the request 1
     * @param driver   the driver
     */
    public static void linkDriverWithRequest(Request request1, Driver driver) //*********************** "dont need" - Reem *****************
    {


    }

    /**
     * Gets driver.
     *
     * @param request1 the request 1
     * @return the driver
     */
    public static Driver getDriver(Request request1) {
        if (request1.isAccepted()) {
            return request1.getDriver();
        }
        else
        {
            return null;
        }
    }

    /**
     * Complete request.
     *
     * @param request1 the request 1
     */
    public static void completeRequest(Request request1) {
    }

    /**
     * Add driver.
     *
     * @param request1 the request 1
     * @param driver2  the driver 2
     */
    public static void addDriver(Request request1, Driver driver2) {
    }

}
