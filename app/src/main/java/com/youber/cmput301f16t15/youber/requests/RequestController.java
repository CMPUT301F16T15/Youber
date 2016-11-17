package com.youber.cmput301f16t15.youber.requests;

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
     * Accept request.
     *
     * @param request the given request
     */
//    public static void acceptRequest(Request request)
//    {
//        request.accept();
//    }

    /**
     * Search request.
     *
     * @param rider the request's rider
     * @return the request
     */
    public static Request searchRequest(Rider rider) //********************** "dont worry about it" - Aaron *********************************
    {
        return null;
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

    public static boolean setPaymentAmount(String amt, Request request) {
        Pattern p = Pattern.compile("\\d+\\.\\d{2}");
        Matcher m = p.matcher(amt);

        if(m.matches()) {
            request.setPayment(Double.parseDouble(amt));
            return true;
        }

        request.setPayment(0); // failed so we want to clear it
        return false;
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

    public static boolean isValidRequest(Request request) {
        if(request.getCost() != 0 && !request.getDescription().isEmpty() && !request.getRouteDistLen().isEmpty())
            return true;

        return false;
    }

    public static void setRouteLenDist(Request request, String routeDesc) {
        // 14km, 19min
        Pattern p = Pattern.compile("\\d+\\.?+\\d+km,\\s{1}.*");
        Matcher m = p.matcher(routeDesc);

        if(!m.matches()) { // Safe guard so coders won't use this improperly (should be used with map)
            throw new RuntimeException();
        }
        else
            request.setRouteDistLen(routeDesc);
    }
}
