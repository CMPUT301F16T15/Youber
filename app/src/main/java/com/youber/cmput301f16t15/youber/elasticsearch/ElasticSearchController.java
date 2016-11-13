package com.youber.cmput301f16t15.youber.elasticsearch;

import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.misc.Observable;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.users.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    public static RequestCollection getRequestsbyGeoLocation(GeoLocation start, Double radiusInKm){
        RequestCollection requestCollection =new RequestCollection();
        String query ="{\n" +
                "    \"filter\" : {\n" +
                "        \"geo_distance\" : {\n" +
                "            \"distance\" : \""+Double.toString(radiusInKm)+"m\",\n" +
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

    public static RequestCollection getRequestsbyKeyWord(String keyword){
        RequestCollection requestCollection =new RequestCollection();
        String query =
                "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"description\" : \""+keyword+"\"\n" +
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
