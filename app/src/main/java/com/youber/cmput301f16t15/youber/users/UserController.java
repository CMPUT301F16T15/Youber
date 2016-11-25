package com.youber.cmput301f16t15.youber.users;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youber.cmput301f16t15.youber.commands.AddUserCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.misc.Observable;
import com.youber.cmput301f16t15.youber.requests.Request;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Jess on 2016-11-08.
 *
 * <p>
 *     Handles user manipulation
 * </p>
 *
 * @see User
 * @see com.youber.cmput301f16t15.youber.users.User.UserType
 */
public class UserController {

    private final static String FILENAME = "user.sav";
    private static Context c;

    private static User user = null;
    public static Observable observable = new Observable();
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
     * @param u the user
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
     * Sets first name.
     *
     * @param firstName the first name
     */
    public static void setFirstName(String firstName) {
        user.setFirstName(firstName);
        update();
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public static void setDateOfBirth(String dateOfBirth) {
        user.setDateOfBirth(dateOfBirth);
        update();
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public static void setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        update();
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public static void setLastName(String lastName) {
        user.setLastName(lastName);
        update();
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public static void setEmail(String email) {
        user.setEmail(email);
        update();
    }

    public static void setUserType(User.UserType userType)
    {
        user.setCurrentUserType(userType);
        update();
    }

    public static void setVehicleMake(String make){
        user.setMake(make);
        update();
    }

    public static void setVehicleModel(String model){
        user.setModel(model);
        update();
    }

    public static void setVehicleYear(String year){
        user.setYear(year);
        update();
    }

    public static void setVehicleColour(String colour){
        user.setColour(colour);
        update();
    }

    public static boolean isRequestContainedInAcceptedDriversUUIDS(UUID uuid) {
        return user.getRequestUUIDs().contains(uuid);
    }

    public static void removeRequestFromAcceptedDriverUUIDS(UUID uuid) {
        user.deleteUUIDFromAccepted(uuid);
        update();
    }

    private static void update() {
        saveUser(user);
        AddUserCommand userCommand = new AddUserCommand(user);
        observable.notifyListeners(userCommand);
    }

    public static void cleanUpDriverList() throws Exception {
        if(user.getCurrentUserType() == User.UserType.rider)
            return;

        HashSet<UUID> tempAcceptedUUIDs = user.getAcceptedDriverUUIDs();
        for(UUID u : tempAcceptedUUIDs) {
            Request esReqeust = ElasticSearchController.getRequest(u);

            String esDriver = esReqeust.getDriverUsernameID();
            if(!esDriver.equals(""))
                // if we are the selected driver, make sure we have it in our completed (extra safe)
                if(esDriver.equals(user.getUsername()))
                    user.addToDriverConfirmed(u);
                else
                    user.removeRequestUUID(u);
        }
    }
}
