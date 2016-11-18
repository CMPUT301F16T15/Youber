package com.youber.cmput301f16t15.youber.requests;

import android.util.Log;

import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.users.Rider;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Reem on 2016-10-24.
 *
 *  <p>
 *     Handles manipulation of requests including confirmation and acceptance drivers
 * </p>
 *
 * @see RequestCollection
 * @see com.youber.cmput301f16t15.youber.misc.Payment
 */
public class RequestController
{
    /**
     * Confirm request.
     *
     * @param request the request
     * @param driver1 the driver
     */
    public static void confirmRequest(Request request, Driver driver1)
    {
        driver1.confirm(request);
    }

    /**
     * Link driver with request.
     *
     * @param request1 the given request
     * @param driver   the driver
     */
    public static void linkDriverWithRequest(Request request1, Driver driver) //*********************** "dont need" - Reem *****************
    {


    }


    /**
     * Gets driver.
     *
     * @param request1 the given request
     * @return the driver
     */
    //NEEDS TO CHANGE
    public static Driver getDriver(Request request1) {
        if (request1.getCurrentStatus()== Request.RequestStatus.riderSelectedDriver) {
            return request1.getDriver();//has to be elastic search controller
        }
        else
        {
            return null;
        }
    }

    /**
     * Complete request.
     *
     * @param request1 the given request
     */
    public static void completeRequest(Request request1) {

    }

    /**
     * Add driver to a request
     *
     * @param request1 the request 1
     * @param driver2  the driver 2
     */
    public static void addDriver(Request request1, Driver driver2) {
    }


    public static  void acceptRequest(Request request){
        request.setAcceptedByDrivers();
    }
    public static  void closeRequest(Request request){
        request.setClosed();
    }
    public static  void payRequest(Request request){
        request.setPaid();
    }


    public static boolean setPaymentAmount(String amt, Request request) throws Exception {
        try {
            Double price = Double.parseDouble(amt);
            request.setPayment(price);
            return true;
        } catch (Exception e) {
            Log.i("Error", "Invalid double string" + e.toString());
            request.setPayment(0);
            return false;
        }
    }

    public static Double getDistanceOfRequest(Request request) {
        return request.getDistance();
    }

    public static Double getEstimatedFare(Request request) { // this is $8 base pay and $2/km
        Double estFare = 8.00;
        double dist = getDistanceOfRequest(request);
        estFare += (2)*dist;

        DecimalFormat df = new DecimalFormat("#.##"); // round it off to 2 decimal places
        return Double.parseDouble(df.format(estFare));
    }

    public static boolean isValidRequest(Request request) {
        if(request.getCost() > 0 && !request.getDescription().isEmpty())
            return true;

        return false;
    }

    public static void setRouteDistance(Request request, Double distance) {
        DecimalFormat df = new DecimalFormat("#.####"); // round it off to 4 decimal places
        Double roundedDist = Double.parseDouble(df.format(distance));
        request.setDistance(roundedDist);
    }

    public static Double getPrice(Request selectedRequest) {
        return selectedRequest.getCost();
    }
}
