package com.youber.cmput301f16t15.youber.commands;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.youber.cmput301f16t15.youber.commands.Command;
import com.youber.cmput301f16t15.youber.requests.Request;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jess on 2016-11-16.
 */

public class MacroCommand {
    private static ArrayList<Command> commands = new ArrayList<Command>();
    private static Context context;

    public static void setContext(Context c) {
        context = c;
    }

    public static void addCommand(Command c) {
        commands.add(c);
        execute();
    }

    public static void execute() {
        for(Command c : commands) {
            if(!isNetworkAvailable())
                break;
            //***********************************can be from c.getExecutionState() == false to !c.getExecutionState()********************
            else if (c.getExecutionState() == false)
                    c.execute();
        }

        cleanupCommandArray();
    }

    private static void cleanupCommandArray() {
        ArrayList<Command> newCommands = new ArrayList<Command>();
        for(Command c : commands) {
            if(c.getExecutionState() == false)
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

