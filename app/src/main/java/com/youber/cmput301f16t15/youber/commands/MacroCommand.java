package com.youber.cmput301f16t15.youber.commands;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.users.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jess on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */

public class MacroCommand {
    private final static String FILE_COMMAND_USER ="user_commands.sav";
    private final static String FILE_COMMAND_ADDREQUEST ="add_request_commands.sav";
    private final static String FILE_COMMAND_DELETEREQUEST ="delete_request_commands.sav";

    private static ArrayList<Command> commands = new ArrayList<>();
    private static Context context;

    public static void setContext(Context c) {
        context = c;
        loadCommands();
        execute();
    }

    public static void addCommand(Command c) {
        commands.add(c);
        execute();
    }

    public static void execute() {
        for(Command c : commands) {
            if(!isNetworkAvailable())
                break;
            else if (!c.isExecuted())
                c.execute();
        }

        cleanupCommandArray();
        saveCommands();
    }

    private static void cleanupCommandArray() {
        ArrayList<Command> newCommands = new ArrayList<Command>();
        for(Command c : commands) {
            if(!c.isExecuted())
                newCommands.add(c);
        }

        commands = newCommands;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // function that helps determine if the request is offline
    //  http://stackoverflow.com/questions/4584541/check-if-a-class-is-subclass-of-another-class-in-java
    public static boolean isRequestContained(UUID uuid) {
        for (Command c : commands) {
            if(c instanceof RequestCommand) {
                Request r = ((RequestCommand)c).getRequest();

                if(r.getUUID() == uuid)
                    return true;
            }
        }

        return false;
    }

    // SAVING AND LOADING COMMANDS
    private static void saveUsers(ArrayList<User> array) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_COMMAND_USER, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(array, writer);
            writer.flush();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to save commands, " + e.toString());
            commands = new ArrayList<>();
        }
    }

    private static void saveRequests(String filename, ArrayList<Request> array) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(array, writer);
            writer.flush();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to save commands, " + e.toString());
            commands = new ArrayList<>();
        }
    }

    private static void saveCommands() {
            ArrayList<Request> addrequests = new ArrayList<>();
            ArrayList<Request> deleterequests = new ArrayList<>();
            ArrayList<User> users = new ArrayList<>();

            for(Command c : commands) {
                if(c instanceof AddRequestCommand)
                    addrequests.add(((AddRequestCommand) c).getRequest());
                else if(c instanceof DeleteRequestCommand)
                    deleterequests.add(((DeleteRequestCommand) c).getRequest());
                else if(c instanceof AddUserCommand)
                    users.add(((AddUserCommand) c).getUser());
            }

            saveRequests(FILE_COMMAND_ADDREQUEST, addrequests);
            saveRequests(FILE_COMMAND_DELETEREQUEST, deleterequests);
            saveUsers(users);
    }


    private static ArrayList<Command> load(String filename) {
        ArrayList<Command> loadedCommands = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType;
            if(filename == FILE_COMMAND_USER) {
                listType = new TypeToken<ArrayList<User>>() {}.getType();
                ArrayList<User> users = gson.fromJson(in, listType);

                for(User u: users)
                    loadedCommands.add(new AddUserCommand(u));
            }
            else {
                listType = new TypeToken<ArrayList<Request>>() {}.getType();
                ArrayList<Request> requests = gson.fromJson(in, listType);

                for(Request r: requests) {
                    if(filename == FILE_COMMAND_ADDREQUEST)
                        loadedCommands.add(new AddRequestCommand(r));
                    else
                        loadedCommands.add(new DeleteRequestCommand(r));
                }
            }

            return loadedCommands;
        }
        catch (Exception e) {
            Log.i("HELP", e.toString());
            return loadedCommands;
        }
    }

    private static void loadCommands() {
        commands.addAll(load(FILE_COMMAND_USER));
        commands.addAll(load(FILE_COMMAND_ADDREQUEST));
        commands.addAll(load(FILE_COMMAND_DELETEREQUEST));
    }
}

