package com.youber.cmput301f16t15.youber;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Reem on 2016-11-11.
 */

public class ElasticSearchController {

    public static Observable observable = new Observable();


    public static ArrayList<Driver> getAcceptedDrivers(Request request)
    {
        ElasticSearchUser.getObjects getter = new ElasticSearchUser.getObjects();
        getter.execute();
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        try {
            ArrayList<User> users = getter.get();

            for (User user: users)
            {
                // this may not work because java is not pass by value
                // if it doesn't then just change it to string.
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



}
