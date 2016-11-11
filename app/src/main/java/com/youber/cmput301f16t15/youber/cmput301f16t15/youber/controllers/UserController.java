package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.elasticsearch.Listener;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.user.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Jess on 2016-11-08.
 */
public class UserController {

    private final static String FILENAME = "user.sav";
    private static Context c;
    private static ArrayList<Listener> listeners = new ArrayList<Listener>();

    private static User user = null;

    /**
     * Gets user.
     *
     * @return the user
     */
    public static User getUser() {
        if(user == null) {
            user = loadUser();
        }

        return user;
    }

    /**
     * Sets context.
     *
     * @param context the context
     */
    public static void setContext(Context context) {
        c = context;
    }

    /**
     * Load user user.
     *
     * @return the user
     */
    public static User loadUser() {
        try
        {
            FileInputStream fis = c.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<User>(){}.getType();
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

    /**
     * Save user.
     *
     * @param u the u
     */
    public static void saveUser(User u) {
        user = u;

        try
        {
            FileOutputStream fos = c.openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            Gson gson = new Gson();
            gson.toJson(user, writer);

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

    /**
     * Add observer.
     *
     * @param obs the obs
     */
//    public static void notifyObservers() {
//
//    }
//
//    public static void addObserver()
    //public static void addListener(Listener obs) {
    //    user.addListener(obs);
    //}

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public static void setFirstName(String firstName) {
        user.setFirstName(firstName);
        saveUser(user);
        notifyObservers();
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public static void setDateOfBirth(String dateOfBirth) {
        user.setDateOfBirth(dateOfBirth);
        saveUser(user);
        notifyObservers();
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public static void setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        saveUser(user);
        notifyObservers();
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public static void setLastName(String lastName) {
        user.setLastName(lastName);
        saveUser(user);
        notifyObservers();
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public static void setEmail(String email) {
        user.setEmail(email);
        saveUser(user);
        notifyObservers();
    }

    public static void setUserType(User.UserType userType)
    {
        user.setCurrentUserType(userType);
        saveUser(user);
        notifyObservers();
    }


    public static void notifyObservers()
    {
        for (Listener listener: listeners)
        {
            listener.update();
        }
    }

    public static void addListener(Listener listener)
    {
        listeners.add(listener);
    }

    public static ArrayList<Listener> getListeners()
    {
        return listeners;
    }
}
