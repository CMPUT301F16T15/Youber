package com.youber.cmput301f16t15.youber;

/**
 * Created by Reem on 2016-10-24.
 */

public class RequestController
{


    public static void addRequest(Request request, Rider rider1)
    {
        rider1.addRequest(request);
    }

    public static void deleteRequest(Request request, Rider rider)
    {
        if (request.isAccepted()) {
            Driver driver = request.getDriver();
            driver.deleteRequest(request);
        }
        rider.deleteRequest(request);
    }

    public static void confirmRequest(Request request, Driver driver1)
    {
        driver1.confirm(request);
    }

    public static void acceptRequest(Request request)
    {
        request.accept();
    }

    public static Request searchRequest(Rider rider) //********************** "dont worry about it" - Aaron *********************************
    {
        return null;
    }

    public static void linkDriverWithRequest(Request request1, Driver driver) //*********************** "dont need" - Reem *****************
    {


    }

    public static Driver getDriver(Request request1) {
        if (request1.isAccepted()) {
            return request1.getDriver();
        }
        else
        {
            return null;
        }
    }

    public static void completeRequest(Request request1) {
    }

    public static void addDriver(Request request1, Driver driver2) {
    }

}
