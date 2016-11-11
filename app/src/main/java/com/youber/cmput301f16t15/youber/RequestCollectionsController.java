package com.youber.cmput301f16t15.youber;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

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
        User.UserType u_type=UserController.getUser().getCurrentUserType();
        RequestCollection requestCollection = (u_type== User.UserType.rider)? riderRequestCollection:driverRequestCollection;
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

    public static void saveRequestCollections( RequestCollection newRequestCollection) {
        RequestCollection requestCollection = newRequestCollection;
        User.UserType u_type=UserController.getUser().getCurrentUserType();
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
        User user =UserController.getUser();
        User.UserType u_type=user.getCurrentUserType();
        user.addRequesttUUID(request.getUUID());
        RequestCollection requestCollection = (u_type== User.UserType.rider)? riderRequestCollection:driverRequestCollection;
        requestCollection.add(request);
        saveRequestCollections(requestCollection);
        observable.notifyListeners();
    }

    public static void deleteRequest(Request request){
        User user =UserController.getUser();
        User.UserType u_type=user.getCurrentUserType();
        user.removeRequestUUID(request.getUUID());
        RequestCollection requestCollection = (u_type== User.UserType.rider)? riderRequestCollection:driverRequestCollection;
        requestCollection.remove(request);
        saveRequestCollections(requestCollection);
        observable.notifyListeners();
    }
}
