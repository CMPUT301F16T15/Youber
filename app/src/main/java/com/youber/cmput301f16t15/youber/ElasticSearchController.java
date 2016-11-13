package com.youber.cmput301f16t15.youber;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.core.Search;

/**
 * Created by Reem on 2016-11-11.
 */



// NOT SURE IT WORKS YET BUT I NEED TO 
public class ElasticSearchController extends ElasticSearch{

    public static Observable observable = new Observable();


    public static ArrayList<Request> getAllRequests()
    {
        ElasticSearchRequest.getObjects getter = new ElasticSearchRequest.getObjects();
        getter.execute();


        ArrayList<Request> requests = null;
        try {
           requests = getter.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return requests;
    }







    public static ArrayList<Driver> getAcceptedDrivers(Request request)
    {
        ElasticSearchUser.getObjects getter = new ElasticSearchUser.getObjects();
        getter.execute();
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        try {
            ArrayList<User> users = getter.get();

            for (User user: users)
            {

                if (user.getDriverUUIDs().contains(request.getUUID()))
                {
                    drivers.add((Driver)user);
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return drivers;
    }

    public static RequestCollection getRequestsbyGeoLocation(GeoLocation start,Double radiusInKm){
        RequestCollection requestCollection =new RequestCollection();
        String query ="{\n" +
                "    \"filter\" : {\n" +
                "        \"geo_distance\" : {\n" +
                "            \"distance\" : \""+Double.toString(radiusInKm)+"km\",\n" +
                "            \"startLocation\" :[ "+Double.toString(start.getLat())+",\n" +
                "            "+Double.toString(start.getLon())+"]\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ElasticSearchRequest.getObjectsByGeolocation getter = new ElasticSearchRequest.getObjectsByGeolocation();
        getter.execute(query);
        try {
            ArrayList<Request> requests = getter.get();
            requestCollection.addAll(requests);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return requestCollection;
    }

}
