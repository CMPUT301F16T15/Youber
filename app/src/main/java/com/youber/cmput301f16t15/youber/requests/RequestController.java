package com.youber.cmput301f16t15.youber.requests;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Reem on 2016-10-24.
 *
 *  <p>
 *     Handles manipulation of requests including confirmation and acceptance drivers
 * </p>
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see RequestCollection
 * @see com.youber.cmput301f16t15.youber.misc.Payment
 */
public class RequestController
{

    /**
     * Complete request.
     *
     * @param request1 the given request
     */
    public static void completeRequest(Request request1) {
        request1.setCompleted();
    }



    public static  void acceptRequest(Request request){
        request.setAcceptedByDrivers();
    }
    public static  void closeRequest(Request request){
        request.setCompleted();
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
        Double estFare = 5.00;
        double dist = getDistanceOfRequest(request);
        estFare += (.48)*dist;

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

    public static String getLocationStr(Geocoder geocoder, GeoLocation loc) {
        String addr = "";

        List<Address> address = null;
        try {
            address = geocoder.getFromLocation(loc.getLat(), loc.getLon(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (address.size() > 0) {
            for (int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++) {
                addr += address.get(0).getAddressLine(i) + "\n";
            }
        }

        return addr;
    }


}
