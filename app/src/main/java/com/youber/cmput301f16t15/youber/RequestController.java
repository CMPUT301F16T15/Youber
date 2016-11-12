package com.youber.cmput301f16t15.youber;

import android.location.Location;

import java.text.DecimalFormat;

/**
 * Created by Reem on 2016-10-24.
 */
public class RequestController
{


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

    public static Double getDistanceOfRequest(Request request) {
        double lat1 = request.getStartLocation().getLat();
        double lat2 = request.getEndLocation().getLat();
        double lon1 = request.getStartLocation().getLon();
        double lon2 = request.getEndLocation().getLon();

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);

        distance = Math.sqrt(distance)/1000; //convert to km
        DecimalFormat df = new DecimalFormat("#.####"); // round it off to 4 decimal places
        return Double.parseDouble(df.format(distance));
    }

    public static Double getEstimatedFare(Request request) { // this is $8 base pay and $2/km
        Double estFare = 8.00;
        double dist = getDistanceOfRequest(request);
        estFare += (2)*dist;

        DecimalFormat df = new DecimalFormat("#.##"); // round it off to 4 decimal places
        return Double.parseDouble(df.format(estFare));
    }
}