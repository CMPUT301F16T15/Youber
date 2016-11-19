package com.youber.cmput301f16t15.youber.commands;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.youber.cmput301f16t15.youber.commands.Command;
import com.youber.cmput301f16t15.youber.requests.Request;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jess on 2016-11-16.
 */

public class MacroCommand {
    private final static String FILENAME_COMMANDS ="macro_commands.sav";

    private static ArrayList<Command> commands = new ArrayList<>();
    private static Context context;

    public static void setContext(Context c) {
        context = c;
        loadCommands();
    }

    private static void saveCommands() {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME_COMMANDS, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Command.class, new CommandSerializer());
            Gson gson = builder.create();

//            Gson gson = new Gson();

            gson.toJson(commands, writer);

            writer.flush();
        }
        catch (Exception e) {
            commands = new ArrayList<>();
        }
    }

    private static void loadCommands() {
        try {
            FileInputStream fis = context.openFileInput(FILENAME_COMMANDS);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Command.class, new CommandSerializer());
            Gson gson = builder.create();

//            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Command>>(){}.getType();
            commands = gson.fromJson(in, listType);
        }
        catch (Exception e) {
            Log.i("HELP", e.toString());
            commands = new ArrayList<Command>();
        }
    }

    public static void addCommand(Command c) {
        commands.add(c);
        execute();
        saveCommands();
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
}

