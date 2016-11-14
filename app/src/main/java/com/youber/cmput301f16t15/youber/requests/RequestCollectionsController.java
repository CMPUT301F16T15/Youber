package com.youber.cmput301f16t15.youber.requests;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youber.cmput301f16t15.youber.misc.Observable;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by aphilips on 11/10/16.
 */

public class RequestCollectionsController {
    private final static String FILENAME_rider = "rider_request_collections.sav";
    private final static String FILENAME_driver ="driver_request_collections.sav";
    private static Context c;

    private static RequestCollection riderRequestCollection = new RequestCollection();
    private static RequestCollection driverRequestCollection= new RequestCollection();

    public static Observable observable = new Observable();

    public static RequestCollection getRequestCollection() {
        User.UserType u_type = UserController.getUser().getCurrentUserType();
        RequestCollection requestCollection = (u_type == User.UserType.rider)? riderRequestCollection:driverRequestCollection;
        if(requestCollection == null) {
            requestCollection = loadRequestCollection();
        }

        return requestCollection;
    }

    public static void setContext(Context context) {
        c = context;
    }

    public static RequestCollection loadRequestCollection() {
        User.UserType u_type=UserController.getUser().getCurrentUserType();
        String FILENAME=(u_type== User.UserType.rider)? FILENAME_rider:FILENAME_driver;
        try
        {
            FileInputStream fis = c.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<RequestCollection>(){}.getType();
            return gson.fromJson(in, listType);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException();
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }
    }

    public static void loadRequestsFromElasticSearch() {

        HashSet<UUID> requestUUIDs = UserController.getUser().getRequestUUIDs();
        for(UUID u : requestUUIDs)
        {
            ElasticSearchRequest.getObjects getRequest = new ElasticSearchRequest.getObjects();
            getRequest.execute(u.toString());
            ArrayList<Request> requests = new ArrayList<Request>();

            try {
               requests  = getRequest.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if(requests.size() == 1) { // found the one!
                if(UserController.getUser().getCurrentUserType() == User.UserType.driver) {
                    driverRequestCollection.add(requests.get(0));
                }
                else {
                    riderRequestCollection.add(requests.get(0));
                }
            }
            else {
                throw new RuntimeException();
            }
        }
    }

    public static void saveRequestCollections( RequestCollection newRequestCollection) {
        RequestCollection requestCollection = newRequestCollection;
        User.UserType u_type = UserController.getUser().getCurrentUserType();
        if(u_type== User.UserType.rider){
            riderRequestCollection=newRequestCollection;
        }else{
            driverRequestCollection=newRequestCollection;
        }
        String FILENAME=(u_type== User.UserType.rider)? FILENAME_rider:FILENAME_driver;
        try
        {
            FileOutputStream fos = c.openFileOutput(FILENAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            Gson gson = new Gson();
            gson.toJson(requestCollection, writer);

            writer.flush();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException();
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }

    }

    public static void addRequest (Request request){
        User user = UserController.getUser();
        User.UserType u_type=user.getCurrentUserType();

        user.addRequesttUUID(request.getUUID());
        RequestCollection requestCollection = (u_type== User.UserType.rider)? riderRequestCollection:driverRequestCollection;
        requestCollection.add(request);

        saveRequestCollections(requestCollection);
        UserController.observable.notifyListeners();
        observable.notifyListeners();
    }

    public static void deleteRequest(Request request){
        User user = UserController.getUser();
        User.UserType u_type = user.getCurrentUserType();
        user.removeRequestUUID(request.getUUID());

        RequestCollection requestCollection = (u_type== User.UserType.rider)? riderRequestCollection:driverRequestCollection;
        requestCollection.remove(request.getUUID());
        saveRequestCollections(requestCollection);

        ElasticSearchRequest.delete deleteRequest = new ElasticSearchRequest.delete();
        deleteRequest.execute(request);

        UserController.observable.notifyListeners();
        observable.notifyListeners();
    }

    public static RequestCollection getOpenRequests() {
        RequestCollection requestsHash = getRequestCollection();
        RequestCollection openRequests = new RequestCollection();

        Collection<Request> requests = requestsHash.values();
        for(Request r: requests) {
            if(r.getCurrentStatus() == Request.RequestStatus.opened)
                openRequests.add(r);
        }

        return openRequests;
    }
}
