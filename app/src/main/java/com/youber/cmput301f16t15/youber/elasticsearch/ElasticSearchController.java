package com.youber.cmput301f16t15.youber.elasticsearch;

import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.gui.LoginActivity;
import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.misc.Observable;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.users.Rider;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Reem on 2016-11-11.
 * <p>
 *     This is the controller class for our elastic search.
 * </p>
 *
 * @see ElasticSearch
 */




// NOT SURE IT WORKS YET BUT I NEED TO 
public class ElasticSearchController extends ElasticSearch{

    /**
     * Do a search for a list of requests
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static ArrayList<Request> getAllRequests() throws Exception
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




    /**
     * Do a search for a list accepted Drivers
     * @param request Request
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static ArrayList<User> getAcceptedDrivers(Request request) {

        String query =
        "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"acceptedRequests\" : \""+request.getUUID().toString()+"\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        ElasticSearchUser.getUserByRequestUUID getter = new ElasticSearchUser.getUserByRequestUUID();
        getter.execute(query);

        try {
            ArrayList<User> users = getter.get();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public static ArrayList<User> getConfirmedDriver(Request request) {

        String query =
                "{\n" +
                        "    \"query\" : {\n" +
                        "        \"match\" : {\n" +
                        "            \"confirmedRequests\" : \""+request.getUUID().toString()+"\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
        ElasticSearchUser.getUserByRequestUUID getter = new ElasticSearchUser.getUserByRequestUUID();
        getter.execute(query);

        try {
            ArrayList<User> users = getter.get();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }







    /**
     * Do a search for a list of requests by their location
     * @return RequestCollection
     * @param start the starting location of a certain request
     * @param radiusInKm the radius from the location with which to search
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static RequestCollection getRequestsbyGeoLocation(GeoLocation start, Double radiusInKm) throws Exception {
        RequestCollection requestCollection =new RequestCollection();

        String query ="{\n" +
            "     \"query\" : {\n"+
            "      \"bool\" : {\n" +
            "            \"should\" :\n" +
            "            [\n" +
            "            {\"match\" : {\"currentStatus\": \"opened\"}},\n" +
            "            {\"match\" : {\"currentStatus\": \"acceptedByDrivers\"}}\n" +
            "            ],\n" +
            "               \"minimum_should_match\" : 1" +
            "              }"+
            "             },"+
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

    /**
     * Do a search for a list of requests by one or more given keywords
     * @return RequestCollection
     * @param keyword a word that will be compared against existing requests to find a match
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static RequestCollection getRequestsbyKeyWord(String keyword) throws Exception {
        RequestCollection requestCollection =new RequestCollection();

        String query = "{\n" +
            "    \"query\" : {\n" +
            "        \"bool\" : {\n" +
            "            \"must\" : { \"match\" : {\"description\" : \""+keyword+"\"}},\n" +
            "            \"should\" :\n" +
            "            [\n" +
            "            {\"match\" : {\"currentStatus\": \"opened\"}},\n" +
            "            {\"match\" : {\"currentStatus\": \"acceptedByDrivers\"}}\n" +
            "            ],\n" +
            "               \"minimum_should_match\" : 1" +
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

    public static User getRider(UUID uuid)
    {

        String query =
        "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"uuids\" : \""+uuid.toString()+"\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        ElasticSearchUser.getUserByRequestUUID getter = new ElasticSearchUser.getUserByRequestUUID();
        getter.execute(query);
        try {
            User user = getter.get().get(0);
            return user;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void setupPutmap(){
        ElasticSearchRequest.addPutMap addPutMap= new ElasticSearchRequest.addPutMap();
        addPutMap.execute("startLocation");
    }


    public static Request getRequest(UUID u) {
        ElasticSearchRequest.getObjects getter = new ElasticSearchRequest.getObjects();
        getter.execute(u.toString());

        try {
            ArrayList<Request> requests = getter.get();
            if(requests.size() == 1)
                return requests.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User getUser(String username) {
        ElasticSearchUser.getObjects searchUser = new ElasticSearchUser.getObjects();
        searchUser.execute(username);

        try {
            ArrayList<User> users = searchUser.get();
            if(users.size() == 1)
                return users.get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
